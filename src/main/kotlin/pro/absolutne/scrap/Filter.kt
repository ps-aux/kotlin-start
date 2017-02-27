package pro.absolutne.scrap

enum class FlatType(val myName: String, val roomsNo: Int) {

    GARZONKA("garzonka", 0),
    ROOM_1("1-izbak", 1),
    ROOM_2("2-izbak", 2),
    ROOM_3("3-izbak", 3),
    ROOM_4("4-izbak", 4),
    ROOM_5("5-izbak", 5),
    ALL("all", Int.MAX_VALUE)
}

enum class Action {
    BUY, SELL
}


class Filter(val action: Action, vararg val flatTypes: FlatType)
