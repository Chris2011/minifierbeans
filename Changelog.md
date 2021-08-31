# Changelog

## Update in 3.4.0

### General
1 - Refactored code to do the same option handling as NetBeans core.  
2 - Updated node dependencies.  

### Fixes
1 - [#73](https://github.com/Chris2011/minifierbeans/issues/73): Every option that was changed will be resetted after NetBeans restart.  
2 - [#85](https://github.com/Chris2011/minifierbeans/issues/85): After reboot Netbeans reset "Minify on save" setting.  

### Features
1 - [#21](https://github.com/Chris2011/minifierbeans/issues/21): New build folder has still the dev files in it.  


## Update in 3.3.1

### General
1 - Added more error handling.  
2 - Updated npm dependencies.  

### Fixes
1 - [#72](https://github.com/Chris2011/minifierbeans/issues/72): Installation in Netbeans 12.3 on OpenJDK 11 with Minifierbeans-3.3.0 not working.  
2 - [#75](https://github.com/Chris2011/minifierbeans/issues/75): Change --language_out from STABLE to other spec.  
3 - [#82](https://github.com/Chris2011/minifierbeans/issues/82): Exception while unzipping.  


## Update in 3.3.0

### General
1 - Simplified and removed code.  

### Features
1 - [#67](https://github.com/Chris2011/minifierbeans/issues/67): Switch from Java based XMLCompressor to npm minify-xml.  


## Updates in 3.2.0

### General
1 - Simplified UI, removed unused code and options. Will be handled via compiler flags.  

### Fixes
1 - [#58](https://github.com/Chris2011/minifierbeans/issues/58): javascript let makes minify fail.  

### Features
1 - [#66](https://github.com/Chris2011/minifierbeans/issues/66): Switch from Java HTMLCompressor to html-minifier-terser.  


## Updates in 3.1.0

### General
1 - Refactored code, removed old options (JS Obfuscate, will be handled later by compiler flags for the google closure compiler).  
2 - Changed the process to check the current and the remote version to update the custom-packages if needed.  
3 - Updated Readme.md.  

### Fixes
1 - [#64](https://github.com/Chris2011/minifierbeans/issues/64): MissingResourceException on action Copy minified CSS.  

### Features
1 - [#61](https://github.com/Chris2011/minifierbeans/issues/61): Switch from Java google closure compiler to the node implementation.  


## Updates in 3.0.0

### General
1 - Changed UI from tabs to a tree view.  
2 - Switched from Ant to Maven.  

### Fixes
1 - [#16](https://github.com/Chris2011/minifierbeans/issues/16): Avoid remove spacing for css calc().  

### Features
1 - [#45](https://github.com/Chris2011/minifierbeans/issues/45): Switch from YUICompressor for NPM CSS Nano.  
2 - [#47](https://github.com/Chris2011/minifierbeans/issues/47): Change result behaviour from dialog to notifications.  


## Updates in 2.5.1

### Fixes
1 - [#49](https://github.com/Chris2011/minifierbeans/issues/49): Cannot install in NB9.  
2 - [#52](https://github.com/Chris2011/minifierbeans/issues/52): Header information cannot be deleted. (PR [#53](https://github.com/Chris2011/minifierbeans/pull/53) by [JayEn84](https://github.com/JayEn84))  

### Features
1 - Added badges to show the license, version, contributors and downloads.  


## Updates in 2.5.0

### Fixes
1 - [#50](https://github.com/Chris2011/minifierbeans/issues/50): Fixed HTML template string escaping.  
2 - [#51](https://github.com/Chris2011/minifierbeans/issues/51): Fixed strict mode.  


## Updates in 2.4.0

### Fixes
1 - [#11](https://github.com/Chris2011/minifierbeans/issues/11): Fixed problematic parameter.  
2 - [#13](https://github.com/Chris2011/minifierbeans/issues/13): Fixed removing use strict.  
3 - [#30](https://github.com/Chris2011/minifierbeans/issues/30): Fixed "short" keyword.  
4 - [#34](https://github.com/Chris2011/minifierbeans/issues/34): Fixed null lin in a JS file while using //.  
5 - [#40](https://github.com/Chris2011/minifierbeans/issues/40): Fixed backtick symbols.  

### Features
1 - Code refactorings
2 - [#43](https://github.com/Chris2011/minifierbeans/issues/43): Switch from YUI Compressor to Google Closure Compiler.  


## Updates in 2.3.0

### General
1 - Removed unused code/files.  
2 - Refactored UI, simplified it, not finished yet.  
3 - Changed some dependencies.
