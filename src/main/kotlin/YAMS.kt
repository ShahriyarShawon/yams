import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL
import java.nio.file.FileAlreadyExistsException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import kotlin.jvm.Throws

class YAMS {
    private var maxNumPageDigits = 0
    private var maxNumChapterDigits = 0
    private var currentWorkingDirectory = Paths.get("").toAbsolutePath().toString()

    @Throws(Exception::class)
    fun downloadLink(url: String, outputDirectory: String){
        try {
            val urlObj = URL(url)
            var fileName = url.split("/").last()
            fileName = outputDirectory+"/"+fileName.padStart(maxNumPageDigits, '0')

            val connection = urlObj.openConnection()
            connection.setRequestProperty("Referer","https://readmanganato.com/")
            val connStream = connection.getInputStream()

            val fileOutStream = FileOutputStream(fileName)
            fileOutStream.write(connStream.readAllBytes())

            fileOutStream.close()
            connStream.close()
            println("Finished Downloading $url to $outputDirectory")
        } catch (e: Exception){
            println("Failed Downloading link: $url")
        }
    }

    @Throws(IOException::class)
    fun downloadChapter(chapterLink: String, outputDirectory: String){
        val outputDir: String = if (outputDirectory == "."){
            currentWorkingDirectory
        } else {
            outputDirectory
        }

        val chapterNum = chapterLink.split("/").last().replace("chapter-", "")
        val chapterDirPath = "$outputDir/$chapterNum"
        val chapterDirPathObj = Paths.get("$outputDir/$chapterNum")

        println("Downloading Chapter $chapterNum to $outputDirectory")

        try {
            Files.createDirectory(chapterDirPathObj)
        }catch (e: FileAlreadyExistsException) {
            println("Directory Already Exists")
        }finally {
            val chapterImageUrls = getChapterImages(chapterLink)
            chapterImageUrls.forEach { url ->
                downloadLink(url, chapterDirPath)
            }
            val listOfFiles = File(chapterDirPath).listFiles()!!
            listOfFiles.sortBy { f -> f.toPath().toString() }

            val zipFileName = "$outputDir/$chapterNum.cbz"

            zipFiles(zipFileName, listOfFiles)
            println("Finished Downloading Chapter $chapterNum to $outputDirectory")
        }
    }

    private fun zipFiles(zipFileName: String, files: Array<File>){
        val fileOutputStream = FileOutputStream(zipFileName)
        val zipFile = ZipOutputStream(fileOutputStream)

        files.forEach { f ->
            val fileInputStream = FileInputStream(f)
            val zipEntry = ZipEntry(f.name)

            zipFile.putNextEntry(zipEntry)

            val byteArr = ByteArray(1024)
            var len = 1

            while(len >= 0) {
                len = fileInputStream.read(byteArr)
                if (len < 0) break
                zipFile.write(byteArr, 0, len)
            }
            fileInputStream.close()
            println("Added $f to $zipFileName")
        }
        zipFile.close()
        fileOutputStream.close()
    }

    fun getChapterImages(chapterLink: String): List<String> {
        val doc = Jsoup.connect(chapterLink).get()
        val containerChapterReader = doc.selectFirst(".container-chapter-reader")!!
        val imgs = containerChapterReader.select("img")
        val urls = imgs.map { x -> x.attr("src") }
        maxNumPageDigits = urls.last().split("/").last().length

        println("Got ${urls.count()} image links from $chapterLink")

        return urls
    }

    fun getChapterLinks(link: String): List<String> {
        val doc = Jsoup.connect(link).get()
        val chapterHolder: Element = doc.select(".row-content-chapter").first()!!
        val chapterLinks = chapterHolder.select(".a-h")
        val links = chapterLinks.map { e ->
            e.selectFirst("a")?.attr("href")!!
        }
        maxNumChapterDigits = links.last().length

        println("Got ${links.count()} image links from $link")

        return links
    }
}