# YAMS
Yet another manganato scraper


Application intended to make downloading manga from [manganato](https://readmanganato.com/)
easy. You give it a chapter link and where you want to save the chapter,
and it will download all the pages (which are just images)
into a directory and then zip that directory up as a cbz (comic book archive) file.

## Installation
```shell
./gradlew assemble
```
Will generate the program in a `.tar` and a `.zip` file located in `build/distributions/`.
Copy one of these and extract it into a directory.
```shell
cd bin
```

If on OSX or Linux, run `./yams {args}`
If on Windows, run `./yams.bat`

## Usage
When this is available to be used this will be the help screen
```sh
yams command [-l LINK_TO_MANGA_OR_CHAPTER] [-o OUTPUT_DIR]

required:
    command [download-chapter|download-manga|help]

optional:
    -l LINK_TO_MANGA_OR_CHAPTER
    -o OUTPUT_DIR
        ^ I don't think I could be any more verbose
```

You can test it with 
```shell
./gradlew build 
./gradlew run --args="download-chapter -l https://readmanganato.com/manga-dr980474/chapter-10 -o /absolute/output/directory"
```
Replace with an output directory

Not providing any examples because I'm sure the commands are verbose as is.



