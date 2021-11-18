# YAMS
Yet another manganato scraper


Application intended to make downloading manga from [mangato](https://readmanganato.com/)
easy. You give it a chapter link and where you want to save the chapter
and it will download all the pages (which are just images)
into a directory and then zip that directory up as a cbz (comic book archive) file.

## Installation
I really have no idea, I'm new to kotlin and java and gradle so I'm just using relying
on IntelliJ to do everything for me. But the source code is here so you can take a look at that.

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

Not providing any examples because I'm sure the commands are verbose as is.



