
def disallowed = /.*[rismouh].*/
def green = /^..ea.$/
def yellow = ["a": [1], "t": [0,3], "e": [1]]

def isGood = {
    word ->
        !(word ==~ disallowed) && (word ==~ green)
                && yellow.entrySet().every(entry ->
                word.contains(entry.key)
                        && !entry.value.any { id -> word[id] == entry.key })
}

def uniqueLetters = {
    (String word) ->
            new HashSet<>(word.toList()).size() == word.size();
}

def wordFile = new File("wordle_words.txt")

if (disallowed.isEmpty()) {
    def words = []
    wordFile.eachLine(word -> {
        if (uniqueLetters(word))
            words.add(word)
    })

    println "Word Count: ${words.size()}"
    def randomWordId = new java.util.Random().nextInt(words.size());
    println "Random Word: ${words[randomWordId]}"
} else {
    int size = 0
    wordFile.eachLine(word -> {
        if (isGood(word)) {
            size++
            println word
        }
    })
    println size
}
