int size = 0

def disallowed = /.*[rismouh].*/
var green = /^..ea.$/
Map<String, List<Integer>> yellow = ["a": [1], "t": [0,3], "e": [1]]

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

def perWord = {
    action -> {
        new File("wordle_words.txt").withReader {br ->
            String word
            while ((word = br.readLine()) != null) {
                action(word)
            }
        }
    }
}

if (disallowed.isEmpty()) {
    def words = []
    perWord(word -> {
        if (uniqueLetters(word))
            words.add(word)
    })

    println words.size()
    println words[new java.util.Random().nextInt(words.size())]
} else {
    perWord(word -> {
        if (isGood(word)) {
            size++
            println word
        }
    })
    println size
}
