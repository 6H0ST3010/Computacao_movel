package event

sealed class Event() {
    class Login(val username: String, val timestamp: Long): Event()
    class Purchase(val username: String, val amount: Double, val timestamp: Long): Event()
    class Logout(val username: String, val timestamp: Long): Event()
}

fun List<Event>.filterByUser(username: String): List<Event>{
    println("Events for $username:")
    return this.filter { event ->
        when (event) {
            is Event.Login -> event.username == username
            is Event.Purchase -> event.username == username
            is Event.Logout -> event.username == username
        }
    }
}

fun List<Event>.totalSpent(username: String) {
    println("Total by $username: $ " +
            this.filterIsInstance<Event.Purchase>()
                .filter{ it.username == username }
                .sumOf { it.amount })
}

fun List<Event>.processEvents(handler: (Event) -> Unit) {
    this.forEach { handler(it) }
}

fun main(){
    val events = listOf (
        Event.Login ("alice", 1_000 ),
        Event.Purchase ("alice", 49.99, 1_100 ),
        Event.Purchase ("bob", 19.99, 1_200 ),
        Event.Login ("bob", 1_050 ),
        Event.Purchase ("alice", 15.00, 1_300 ),
        Event.Logout ("alice", 1_400),
        Event.Logout ("bob", 1_500)
    )
    events.processEvents {event ->
        val message = when (event) {
            is Event.Login ->
                "[LOGIN]    ${event.username} logged in at t=${event.timestamp}"
            is Event.Purchase ->
                "[PURCHASE] ${event.username} spent $${event.amount} at t=${event.timestamp}"
            is Event.Logout ->
                "[LOGOUT]   ${event.username} logged out at t=${event.timestamp}"
        }
        println(message)}

    events.totalSpent("alice")
    events.totalSpent("bob")

    events.filterByUser("alice").processEvents { event ->
        when (event) {
            is Event.Login ->
                println("   Login(username=${event.username}, timestamp=${event.timestamp})")
            is Event.Purchase ->
                println("   Purchase(username=${event.username}, amount=${event.amount}, timestamp=${event.timestamp})")
            is Event.Logout ->
                println("   Logout(username=${event.username}, timestamp=${event.timestamp})")
        }
    }
}