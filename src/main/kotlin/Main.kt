import YAMS

val COMMANDS = listOf<String>(
    "download-chapter",
    "download-manga",
    "help"
)

val VALID_FLAGS = listOf(
    "-l",
    "-o",
)

const val USAGE = """
yams command [-l LINK_TO_MANGA_OR_CHAPTER] [-o OUTPUT_DIR]

required:
    command [download-chapter|download-manga|help]

optional:
    -l LINK_TO_MANGA_OR_CHAPTER
    -o OUTPUT_DIR
        ^ I don't think I could be any more verbose
"""


fun main(args: Array<String>){
    if (args.count() == 0 || args[0] == "help"){
        println(USAGE)
        return
    }
    // going to bed, will implement better arg parser soon
    val commandToRun = args[0]

    try {
        val link = args[2]
        val outputDir = args[4]

        println("Running $commandToRun with the link $link to $outputDir")
        val yam = YAMS()
        if (commandToRun == "download-chapter"){
            yam.downloadChapter(link, outputDir)
        }
        else {
            // download manga not implemented yet
            println("Pretty sure $commandToRun isn't implemented yet")
        }
    }catch (e: IndexOutOfBoundsException){
        println("Failed")
        return
    }


}