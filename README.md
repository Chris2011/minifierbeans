### Looking for Contributors (Feel free to contact me if you are interested : christian.lenz@gmx.net)

# ATTENTION
If you got an error which is similiar to this:

```
...
[ERROR] in -
  3:7:syntax error
[ERROR] in -
  1:0:Compilation produced 2 syntax errors.
org.mozilla.javascript.EvaluatorException: Compilation produced 2 syntax errors.
...
```

This happens in case that the YUICompressor, which is used to minify JS, can't handle ES6 syntax. Please check your JS code before, on this page:

http://refresh-sf.com/

If you got the same or similar error, it is related to the YUICompressor, which was mentioned.
It would be great to go to the repo of the YUICompressor and create or vote for similar tickets there.
Thx.

# JS CSS Minify Compress [![][license img]][license]

Plugin to minify JS , CSS and HTML for quicker page load times by reducing the size of js/css/html files.
This plugin remove all unnecessary characters from source code, without changing its functionality. These unnecessary characters usually include white space characters, new line characters, comments, and sometimes block delimiters, which are used to add readability to the code but are not required for it to execute.

- To minify all js , css or html , right click on folder in netbeans and select Minify WEB Content option ; it will minify all css & js file.
- To minify single js/css/html file , right click on js/css/html file in netbeans and select Minify JS/CSS/HTML option to minify file.

Also optimize by compressing image PNG/JPEG (Currently Supported format) and provides other functionality such as image Base64 Encoder/Decoder .

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


<img src="http://plugins.netbeans.org/data/images/1385563254_Final.png">

## Communication

- Email : [christian.lenz@gmx.net](mailto:christian.lenz@gmx.net)
- Twitter: [@Chrizzly42](https://twitter.com/Chrizzly42)
- [GitHub Issues](https://github.com/Chris2011/js-css-minify-compress/issues)


## Bugs and Feedback

For bugs, questions and discussions please use the [Github Issues](https://github.com/Chris2011/js-css-minify-compress/issues).


#### If you like:heart: this project, don't forget:blush: to give us a star:star2: on GitHub!

[license]:LICENSE
[license img]:https://img.shields.io/badge/License-Apache%202-blue.svg
