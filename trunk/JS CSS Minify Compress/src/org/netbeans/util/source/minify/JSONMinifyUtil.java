package org.netbeans.util.source.minify;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONMinifyUtil {

    public String minify(String jsonString) {
        String tokenizer = "\"|(/\\*)|(\\*/)|(//)|\\n|\\r";
        String magic = "(\\\\)*$";
        Boolean in_string = false;
        Boolean in_multiline_comment = false;
        Boolean in_singleline_comment = false;
        String tmp = "";
        String tmp2 = "";
        List<String> new_str = new ArrayList<String>();
        Integer from = 0;
        String lc = "";
        String rc = "";

        Pattern pattern = Pattern.compile(tokenizer);
        Matcher matcher = pattern.matcher(jsonString);

        Pattern magicPattern = Pattern.compile(magic);
        Matcher magicMatcher = null;
        Boolean foundMagic = false;

        if (!matcher.find()) {
            return jsonString;
        } else {
            matcher.reset();
        }

        while (matcher.find()) {
            lc = jsonString.substring(0, matcher.start());
            rc = jsonString.substring(matcher.end(), jsonString.length());
            tmp = jsonString.substring(matcher.start(), matcher.end());

            if (!in_multiline_comment && !in_singleline_comment) {
                tmp2 = lc.substring(from);
                if (!in_string) {
                    tmp2 = tmp2.replaceAll("(\\n|\\r|\\s)*", "");
                }

                new_str.add(tmp2);
            }
            from = matcher.end();

            if (tmp.charAt(0) == '\"' && !in_multiline_comment && !in_singleline_comment) {
                magicMatcher = magicPattern.matcher(lc);
                foundMagic = magicMatcher.find();
                if (!in_string || !foundMagic || (magicMatcher.end() - magicMatcher.start()) % 2 == 0) {
                    in_string = !in_string;
                }
                from--;
                rc = jsonString.substring(from);
            } else if (tmp.startsWith("/*") && !in_string && !in_multiline_comment && !in_singleline_comment) {
                in_multiline_comment = true;
            } else if (tmp.startsWith("*/") && !in_string && in_multiline_comment && !in_singleline_comment) {
                in_multiline_comment = false;
            } else if (tmp.startsWith("//") && !in_string && !in_multiline_comment && !in_singleline_comment) {
                in_singleline_comment = true;
            } else if ((tmp.startsWith("\n") || tmp.startsWith("\r")) && !in_string && !in_multiline_comment && in_singleline_comment) {
                in_singleline_comment = false;
            } else if (!in_multiline_comment && !in_singleline_comment && !tmp.substring(0, 1).matches("\\n|\\r|\\s")) {
                new_str.add(tmp);
            }
        }

        new_str.add(rc);
        StringBuffer sb = new StringBuffer();
        for (String str : new_str) {
            sb.append(str);
        }

        return sb.toString();
    }

}
