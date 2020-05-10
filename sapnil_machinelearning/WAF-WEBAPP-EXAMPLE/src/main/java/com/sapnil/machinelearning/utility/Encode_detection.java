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

    public static boolean isUtf8Encoded(String url) {
        return isAlphaNumeric(url);
    }

    public static boolean isUrlUtf8Encoded(String url)
            throws UnsupportedEncodingException {
        return isAlphaNumeric(URLDecoder.decode(url, "UTF-8"));
    }

    //UTF-16BE
    public static boolean isUrlUTF16BEEncoded(String url)
            throws UnsupportedEncodingException {
        return isAlphaNumeric(URLDecoder.decode(url, "UTF-16BE"));
    }

    //UTF-16LE
    public static boolean isUrlUTF16BEBEEncoded(String url)
            throws UnsupportedEncodingException {
        return isAlphaNumeric(URLDecoder.decode(url, "UTF-16BE"));
    }

    //UTF-16
    public static boolean isUrlUTF16Encoded(String url)
            throws UnsupportedEncodingException {
        return isAlphaNumeric(URLDecoder.decode(url, "UTF-16"));
    }

    public static boolean isUrlUSASCIIEncode(String url)
            throws UnsupportedEncodingException {
        return isAlphaNumeric(URLDecoder.decode(url, "US-ASCII"));
    }

    public static boolean isUrlIsoEncoded(String url)
            throws UnsupportedEncodingException {
        return isAlphaNumeric(URLDecoder.decode(url, "ISO-8859-1"));
    }

    private static boolean isAlphaNumeric(String decode) {
        for (char c : decode.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                return false;
            }
        }
        return true;
    }

}
