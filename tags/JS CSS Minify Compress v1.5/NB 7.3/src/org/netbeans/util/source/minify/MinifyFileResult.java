/**
 * Copyright [2013] Gaurav Gupta
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.netbeans.util.source.minify;


public class MinifyFileResult {
 private long inputFileSize;
 private long outputFileSize;

    @Override
    public String toString() {
        return "MinifyFileResult{" + "inputFileSize=" + inputFileSize + ", outputFileSize=" + outputFileSize + '}';
    }

    /**
     * @return the inputFileSize
     */
    public long getInputFileSize() {
        return inputFileSize;
    }

    /**
     * @param inputFileSize the inputFileSize to set
     */
    public void setInputFileSize(long inputFileSize) {
        this.inputFileSize = inputFileSize;
    }

    /**
     * @return the outputFileSize
     */
    public long getOutputFileSize() {
        return outputFileSize;
    }

    /**
     * @param outputFileSize the outputFileSize to set
     */
    public void setOutputFileSize(long outputFileSize) {
        this.outputFileSize = outputFileSize;
    }

   
 
}
