package utils

class StringFormatter {

    static String capitalizarTodaString(String string) {
        if (string)
            return string.tokenize().collect { it.toLowerCase().capitalize() }.join(' ')
        return string
    }
}
