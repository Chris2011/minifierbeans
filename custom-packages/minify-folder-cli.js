/* global process, Promise */

const ClosureCompiler = require('./node_modules/google-closure-compiler').compiler;

if (process.argv.includes('--debug')) {
    console.log(ClosureCompiler.COMPILER_PATH); // absolute path to the compiler jar
    console.log(ClosureCompiler.CONTRIB_PATH); // absolute path to the contrib folder which contain externs
}

let lang = '';
const cliArr = [];
const cliRunners = [];

if (process.argv.includes('--lang')) {
    lang = process.argv.slice(process.argv.indexOf('--lang') + 1);
} else {
    console.log('ERROR: --lang arg needs to be defined. Could be css, js, html or xml');
    process.exit(1);
}

if (process.argv.includes('--files')) {
    const files = process.argv.slice(process.argv.indexOf('--files') + 1);

    if (files.length) {
        files[0].split(';').forEach((file) => {
            cliArr.push(JSON.parse(file));
        });
    }
}

console.log('Minification in progress...');

cliArr.forEach((inOut) => {
    switch (lang[0]) {
        case 'css':
            console.log("cssnano");
            break;
        case 'html':
            console.log("html-minifier-terser");
            break;
        case 'js':
            // Existing files.
            // --files "{\"in\":\"postcss.config.js\",\"out\":\"postcss.config.min.js\"};{\"in\":\"file2.js\",\"out\":\"file2.min.js\"};{\"in\":\"file2 - Kopie.js\",\"out\":\"file2 - Kopie.min.js\"}"

            // Existing files with one not existing file.
            // --files "{\"in\":\"postcss.config.js\",\"out\":\"postcss.config.min.js\"};{\"in\":\"file2.js\",\"out\":\"file2.min.js\"};{\"in\":\"file2 - Kopie.js\",\"out\":\"file2 - Kopie.min.js\"};{\"in\":\"filel.js\",\"out\":\"file1.min.js\"}"
            cliRunners.push(googleClosureCompiler(inOut));
            break;
        case 'xml':
            console.log("minify-xml");
            break;
        default:
            break;
    }
});

Promise.all(cliRunners).then((data) => {
    if (data.length) {
        console.log(`Minification successful for: ${data}`);
    } else {
        console.log('Nothing to minify.');
    }
}).catch((err) => {
    console.log(`Unsuccessful minifcation for ${err.file}: ${err.message}`);
});

function googleClosureCompiler(inOut) {
    const closureCompiler = new ClosureCompiler({
        js: inOut.in,
        compilation_level: 'simple',
        js_output_file: inOut.out
    });

    return new Promise((resolve, reject) => {
        return closureCompiler.run((exitCode, stdOut, stdErr) => {
            if (stdErr) {
                return reject({file: inOut.in, message: stdErr});
            }

            //compilation complete
            return resolve(inOut.in);
        });
    });
}