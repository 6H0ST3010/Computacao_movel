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

    println("Result:")
    samplePipeline.execute(logs).forEach { println(it) }
}