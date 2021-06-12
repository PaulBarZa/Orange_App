package fr.epf.min.projet_paul_barthe_min2.utils


fun transformNutriscore(nutriscore: String): Array<String>{
    return when(nutriscore){
        "a" -> arrayOf("Grade A: Enjoy yourself (in moderation ... or not)", "#32CD32")
        "b" -> arrayOf("Grade B: That's pretty good", "#9ACD32")
        "c" -> arrayOf("Grade C: Once in a while ... no more !", "#E2C10F")
        "d" -> arrayOf("Grade D: Put it back now !", "#E00000")
        "e" -> arrayOf("Grade E: I didn't even know this stuff was edible", "#E00000")
        else -> arrayOf("Nutriscore not found", "black")
    }
}
fun transformEcocore(ecoscore: String): Array<String>{
    return when(ecoscore){
        "a" -> arrayOf("Grade A: Almost perfect ", "#32CD32")
        "b" -> arrayOf("Grade B: On the right way ", "#9ACD32")
        "c" -> arrayOf("Grade C: Ecology is not their strength", "#E2C10F")
        "d" -> arrayOf("Grade D: Don't bring in a plane just for that ... ", "#E00000")
        "e" -> arrayOf("Grade E: I didn't even know this stuff was crossing borders !", "#E00000")
        else -> arrayOf("Ecoscore not found", "black")
    }
}