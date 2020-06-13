/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sapnil.machinelearning.utility;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 *
 * @author Nasir uddin
 */
/*
US-ASCII	Seven-bit ASCII, a.k.a. ISO646-US, a.k.a. the Basic Latin block of the Unicode character set
ISO-8859-1  	ISO Latin Alphabet No. 1, a.k.a. ISO-LATIN-1
UTF-8	Eight-bit UCS Transformation Format
UTF-16BE	Sixteen-bit UCS Transformation Format, big-endian byte order
UTF-16LE	Sixteen-bit UCS Transformation Format, little-endian byte order
UTF-16	Sixteen-bit UCS Transformation Format, byte order identified by an optional byte-order mark*/
public class Encode_detection {

    public static boolean isUrldoubleencoding(String encode_param)
            throws UnsupportedEncodingException {
        return isAlphaNumeric(URLDecoder.decode(URLDecoder.decode(encode_param)));
    }

    private static boolean isAlphaNumeric(String decode) {
        for (char c : decode.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isUrlencoding(String encode_param)
            throws UnsupportedEncodingException {
        return isAlphaNumeric(URLDecoder.decode(URLDecoder.decode(encode_param)));
    }

    /**
     * *
     * this function support hexadecimal encoding/URL encoded characters and
     * UTF_8 charset encode and ISO_8859_1 charset and US_ASCII and UTF_16 and
     * UTF_16BE and UTF_16LE
     *
     * @param encode_param
     * @return
     */
    public static String decode(String encode_param) {
        String decode_url =null;
        try {

            if (isUrldoubleencoding(encode_param)) {
                 return URLDecoder.decode(URLDecoder.decode(encode_param));
            } else if (isUrlencoding(encode_param)) {
                return URLDecoder.decode(encode_param);
            } else {
                return encode_param;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return "";
    }

}
