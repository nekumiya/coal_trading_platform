package com.guet.coal_trading_platform.common.utils;

import com.guet.coal_trading_platform.dto.RSAKey;
import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author: Hps
 * @date: 2021/3/13 18:47
 * @description: RSA加密工具类，提供公钥加密、解密算法，密钥加密、解密算法
 */
@Component
public class RSAUtils {
    public static final String CHARSET = "UTF-8";
    public static final String RSA_ALGORITHM = "RSA";
    /**
     * 创建RSA 公钥-私钥
     * @return
     *
     */
    public static RSAKey createKeys(){
        return createKey(1024);
    }
    /**
     * 创建RSA公钥-私钥
     */
    public static RSAKey createKey(int keySize)  {
        KeyPairGenerator kpg=null;
        try {
            kpg= KeyPairGenerator.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        //初始化长度
        kpg.initialize(keySize);
        //生成密钥对
        KeyPair keyPair=kpg.generateKeyPair();
        //获取公钥
        PublicKey publicKey = keyPair.getPublic();

        String publicKeyStr = new String(Base64.encodeBase64(publicKey.getEncoded()));

        //获取私钥
        Key privateKey=keyPair.getPrivate();
        String privateKeyStr = new String(Base64.encodeBase64(privateKey.getEncoded()));

        RSAKey rsaKey = new RSAKey();
        rsaKey.setPublicKey(publicKeyStr);
        rsaKey.setPrivateKey(privateKeyStr);
        return  rsaKey;
    }

    /**
     * 公钥加密
     * @param originalText 原文
     * @param publicKey
     * @return
     */
    public static String publicEncrypt(String originalText, String publicKey){
        RSAPublicKey rsaPublicKey = getPublicKey(publicKey);
        return publicEncrypt(originalText,rsaPublicKey);
    }

    /**
     * 公钥解密
     * @param cipherText
     * @param publicKey
     * @return
     */
    public static String publicDecrypt(String cipherText, String publicKey){
        RSAPublicKey rsaPublicKey = getPublicKey(publicKey);
        return publicDecrypt(cipherText,rsaPublicKey);
    }

    /**
     * 私钥加密
     * @param originalText
     * @param privateKey
     * @return
     */
    public static String privateEncrypt(String originalText, String privateKey){
        RSAPrivateKey rsaPrivateKey=  getPrivateKey(privateKey);
        return privateEncrypt(originalText,rsaPrivateKey);
    }


    /**
     * 私钥解密
     * @param cipherText 密文
     * @param privateKey
     * @return
     */
    public static String privateDecrypt(String cipherText, String privateKey){
        RSAPrivateKey rsaPrivateKey=  getPrivateKey(privateKey);
        return privateDecrypt(cipherText,rsaPrivateKey);
    }




    private static RSAPublicKey getPublicKey(String publicKey){
        //密钥工厂
        KeyFactory keyFactory=null;
        try {
            keyFactory= KeyFactory.getInstance(RSA_ALGORITHM);
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        //按照X509标准进行编码密钥
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKey));
        RSAPublicKey key=null;
        try {
            //生成公钥
            key = (RSAPublicKey)keyFactory.generatePublic(x509EncodedKeySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return key;

    }


    /**
     * 得到私钥
     * @param privateKey 密钥字符串（经过base64编码）
     * @throws Exception
     */
    private static RSAPrivateKey getPrivateKey(String privateKey){
        //通过PKCS#8编码的Key指令获得私钥对象
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance(RSA_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKey));
        RSAPrivateKey key = null;
        try {
            key = (RSAPrivateKey) keyFactory.generatePrivate(pkcs8KeySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return key;
    }

    /**
     * 公钥加密
     * @param originalText
     * @param publicKey
     * @return
     */
    private static String publicEncrypt(String originalText, RSAPublicKey publicKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return Base64.encodeBase64URLSafeString(cipher.doFinal(originalText.getBytes()));
        }catch(Exception e){
            throw new RuntimeException("加密字符串[" + originalText + "]时遇到异常", e);
        }
    }
    /**
     * 私钥解密
     * @param cipherText
     * @param privateKey
     * @return
     */

    private static String privateDecrypt(String cipherText, RSAPrivateKey privateKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return new String(cipher.doFinal(Base64.decodeBase64(cipherText)), CHARSET);
        }catch(Exception e){
            throw new RuntimeException("解密字符串[" + cipherText + "]时遇到异常", e);
        }
    }

    private static String privateEncrypt(String originalText, RSAPrivateKey privateKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            return Base64.encodeBase64URLSafeString(cipher.doFinal(originalText.getBytes()));
        }catch(Exception e){
            throw new RuntimeException("加密字符串[" + originalText + "]时遇到异常", e);
        }
    }

    private static String publicDecrypt(String cipherText, RSAPublicKey publicKey){
        try{
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            return new String(cipher.doFinal(Base64.decodeBase64(cipherText)), CHARSET);
        }catch(Exception e){
            throw new RuntimeException("解密字符串[" + cipherText + "]时遇到异常", e);
        }
    }

    private static byte[] rsaSplitCodec(Cipher cipher, int opmode, byte[] datas, int keySize){
        int maxBlock = 0;
        if(opmode == Cipher.DECRYPT_MODE){
            maxBlock = keySize / 8;
        }else{
            maxBlock = keySize / 8 - 11;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] buff;
        int i = 0;
        try{
            while(datas.length > offSet){
                if(datas.length-offSet > maxBlock){
                    buff = cipher.doFinal(datas, offSet, maxBlock);
                }else{
                    buff = cipher.doFinal(datas, offSet, datas.length-offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
        }catch(Exception e){
            throw new RuntimeException("加解密阀值为["+maxBlock+"]的数据时发生异常", e);
        }
        byte[] resultDatas = out.toByteArray();
        IOUtils.closeQuietly(out);
        return resultDatas;
    }

}
