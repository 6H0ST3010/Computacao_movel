package pipeline

class Pipeline {
    data class Stage (val name: String, val transform: (List<String>) -> List<String>)
    val stages = mutableListOf<Stage>()

    fun addStage(name: String, transform: (List<String>) -> List<String>) {
        stages.add(Stage(name, transform))
    }

    fun execute(input: List<String>): List<String> {
        var result = input
        stages.forEach { stage ->
            result = stage.transform(result)
        }
        return result
    }

    fun describe(){
        if(stages.isEmpty()){
            println("Stage is empty")
        }
        else{
            println("Pipeline stages:")
            stages.forEachIndexed { index, stage ->
                println("   ${index + 1}. ${stage.name}")
            }
        }
    }

    fun compose(sname1: String, sname2: String){
        val stage1 = stages.find { it.name == sname1 }
        val stage2 = stages.find { it.name == sname2 }
        val newstage = "$sname1 & $sname2"

        if (stage1 == null || stage2 == null){
            println("Stages not found")
            return
        }

        stages.remove(stage1)
        stages.remove(stage2)

        val newtransformation = { input: List<String> ->
            stage2.transform(stage1.transform(input))
        }
        addStage(newstage, newtransformation)
    }

    fun fork(pipe1: Pipeline, pipe2: Pipeline, input: List<String>): Pair<List<String>, List<String>>{
        val res1 = pipe1.execute(input)
        val res2 = pipe2.execute(input)

        return Pair(res1, res2)
    }
}

fun buildPipeline(block: Pipeline.() -> Unit): Pipeline {
    val pipeline = Pipeline()
    pipeline.block()
    return pipeline
}

fun main(){
    val logs = listOf (
        " INFO: server started ",
        " ERROR: disk full ",
        " DEBUG: checking config ",
        " ERROR: out of memory ",
        " INFO: request received ",
        " ERROR: connection timeout "
    )

    val samplePipeline = buildPipeline{
        addStage("Trim"){ lines ->
            lines.map{it.trim()}
        }

        addStage("Filter errors"){lines ->
            lines.filter{it.contains("ERROR")}
        }

        addStage("Uppercase"){lines ->
            lines.map{it.uppercase()}
        }

        addStage("Add index"){lines ->
            lines.mapIndexed { index, string -> "${index + 1}. $string" }
        }
    }

    samplePipeline.describe()

    samplePipeline.compose("Trim", "Uppercase")
    samplePipeline.describe()

    println("Result:")
    samplePipeline.execute(logs).forEach { println(it) }

    val altPipeline = buildPipeline{
        addStage("Lowercase"){ lines ->
            lines.map{it.lowercase()}
        }
    }
    val mainPipeline = Pipeline()
    println("Fork result:")
    val (res1, res2) = mainPipeline.fork(samplePipeline,altPipeline, logs)
    println("Result fork:")
    res1.forEach { println(it) }
    res2.forEach { println(it) }
}