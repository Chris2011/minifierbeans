# MinifierBeans 

![License](https://img.shields.io/github/license/Chris2011/minifierbeans.svg)
![Release](https://img.shields.io/github/release/Chris2011/minifierbeans.svg)
![Contributors](https://img.shields.io/github/contributors/chris2011/minifierbeans.svg)
![Downloads](https://img.shields.io/github/downloads/chris2011/minifierbeans/total.svg)

# ⚠️ KNOWN BUG ⚠️
Atm there is a problem after installing while extracting. If you encounter the same problem like an exception while extracting or while using a minifier action please make sure that you have the latest custom-packages version 1.5.1 (can be found in package.json) and manually extract the archive into your usersfolder/.netbeans/minifierbeans/custom-packages/... (custom-packages/custom-packages is not the correct path)

## Description

Plugin to minify JS , CSS and HTML for quicker page load times by reducing the size of js/css/html files.
This plugin remove all unnecessary characters from source code, without changing its functionality. These unnecessary characters usually include white space characters, new line characters, comments, and sometimes block delimiters, which are used to add readability to the code but are not required for it to execute.

- To minify all js , css or html , right click on folder in netbeans and select Minify WEB Content option ; it will minify all css & js file.
- To minify single js/css/html file , right click on js/css/html file in netbeans and select Minify JS/CSS/HTML option to minify file.

Also optimize by compressing image PNG/JPEG (Currently Supported format) and provides other functionality such as image Base64 Encoder/Decoder .


## Compiler used

#### JS
    - Single file action -> Google Closure Compiler (Node based)
    - Minify WEB Content -> YUI Compressor (Unmaintained - Java based) - #46
    - Copy minifed JS -> YUI Compressor (Unmaintained - Java based) #46
#### CSS
    - Single file action -> PostCSS with CSSNano (Node based)
    - Minify WEB Content -> YUI Compressor (Unmaintained - Java based) #46
    - Copy minifed CSS -> YUI Compressor (Unmaintained - Java based) #46
#### HTML
    - Single file action -> html-minifier-terser (Node based)
    - Minify WEB Content -> htmlcompressor (Unmaintained - Java based) #46
    - Copy minifed HTML -> htmlcompressor (Unmaintained - Java based) #46
#### XML
    - Single file action -> Minify-XML (Node based)
    - Minify WEB Content -> htmlcompressor (Unmaintained - Java based) #46
    - Copy minifed XML -> htmlcompressor (Unmaintained - Java based) #46
#### JSON - Custom implementation
#### Images - PngTastic (Java based) [#46](https://github.com/Chris2011/minifierbeans/issues/46)


## What does it do?

##### 1- Minify JS File
##### 2- Minify JS Source Code Snippet by selecting in editor
##### 3- Minify CSS File
##### 4- Minify CSS Source Code Snippet by selecting in editor
##### 5- Minify HTML File
##### 6- Minify HTML Source Code Snippet by selecting in editor
##### 7- Minify XML File
##### 8- Minify XML Snippet by selecting in editor
##### 9- Minify JSON File
##### 10- Minify JSON Snippet by selecting in editor
##### 11- Log Evaluation Input/Output File Size , Space Reduced Percentage , Total Time Taken ,Individual Log appended to file 
##### 12- Image Base64 Encoder/Decoder
##### 13- Image PNG/JPEG Compress (Currently Supported format)

![Minifierbeans Final](./screenshots/minifierbeans-final.jpg)

![Minifierbeans all tabs](./screenshots/minifierbeans-all-tabs.png)


## Changelog

See [Changelog](./Changelog.md) for all information  


## NetBeans Compatibility

Version < 2.3.0 is compatible to NetBeans 8.2 (Which is not supported anymore)  
Version >= 2.5.1 is compatible to Apache NetBeans >= 9.0  
  
Plugin is available at https://github.com/Chris2011/minifierbeans/releases/latest
