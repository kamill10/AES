package org.example.krypto;

public class AES {
    private byte[][] keyForAllRounds;
    static byte[]originalKey;
    static int Nk;
    static int Nb;
    static int Nr;
    private static final int[] sbox = {0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F,
            0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE, 0xD7, 0xAB, 0x76, 0xCA, 0x82,
            0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4, 0xA2, 0xAF, 0x9C,
            0xA4, 0x72, 0xC0, 0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7, 0xCC,
            0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15, 0x04, 0xC7, 0x23,
            0xC3, 0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27,
            0xB2, 0x75, 0x09, 0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52,
            0x3B, 0xD6, 0xB3, 0x29, 0xE3, 0x2F, 0x84, 0x53, 0xD1, 0x00, 0xED,
            0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE, 0x39, 0x4A, 0x4C, 0x58,
            0xCF, 0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85, 0x45, 0xF9,
            0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8, 0x51, 0xA3, 0x40, 0x8F, 0x92,
            0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2,
            0xCD, 0x0C, 0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E,
            0x3D, 0x64, 0x5D, 0x19, 0x73, 0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A,
            0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14, 0xDE, 0x5E, 0x0B, 0xDB, 0xE0,
            0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2, 0xD3, 0xAC, 0x62,
            0x91, 0x95, 0xE4, 0x79, 0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5, 0x4E,
            0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08, 0xBA, 0x78,
            0x25, 0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B,
            0xBD, 0x8B, 0x8A, 0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E,
            0x61, 0x35, 0x57, 0xB9, 0x86, 0xC1, 0x1D, 0x9E, 0xE1, 0xF8, 0x98,
            0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E, 0x87, 0xE9, 0xCE, 0x55,
            0x28, 0xDF, 0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42, 0x68, 0x41,
            0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16};

    private final int[] inv_sbox = {0x52, 0x09, 0x6A, 0xD5, 0x30, 0x36, 0xA5,
            0x38, 0xBF, 0x40, 0xA3, 0x9E, 0x81, 0xF3, 0xD7, 0xFB, 0x7C, 0xE3,
            0x39, 0x82, 0x9B, 0x2F, 0xFF, 0x87, 0x34, 0x8E, 0x43, 0x44, 0xC4,
            0xDE, 0xE9, 0xCB, 0x54, 0x7B, 0x94, 0x32, 0xA6, 0xC2, 0x23, 0x3D,
            0xEE, 0x4C, 0x95, 0x0B, 0x42, 0xFA, 0xC3, 0x4E, 0x08, 0x2E, 0xA1,
            0x66, 0x28, 0xD9, 0x24, 0xB2, 0x76, 0x5B, 0xA2, 0x49, 0x6D, 0x8B,
            0xD1, 0x25, 0x72, 0xF8, 0xF6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xD4,
            0xA4, 0x5C, 0xCC, 0x5D, 0x65, 0xB6, 0x92, 0x6C, 0x70, 0x48, 0x50,
            0xFD, 0xED, 0xB9, 0xDA, 0x5E, 0x15, 0x46, 0x57, 0xA7, 0x8D, 0x9D,
            0x84, 0x90, 0xD8, 0xAB, 0x00, 0x8C, 0xBC, 0xD3, 0x0A, 0xF7, 0xE4,
            0x58, 0x05, 0xB8, 0xB3, 0x45, 0x06, 0xD0, 0x2C, 0x1E, 0x8F, 0xCA,
            0x3F, 0x0F, 0x02, 0xC1, 0xAF, 0xBD, 0x03, 0x01, 0x13, 0x8A, 0x6B,
            0x3A, 0x91, 0x11, 0x41, 0x4F, 0x67, 0xDC, 0xEA, 0x97, 0xF2, 0xCF,
            0xCE, 0xF0, 0xB4, 0xE6, 0x73, 0x96, 0xAC, 0x74, 0x22, 0xE7, 0xAD,
            0x35, 0x85, 0xE2, 0xF9, 0x37, 0xE8, 0x1C, 0x75, 0xDF, 0x6E, 0x47,
            0xF1, 0x1A, 0x71, 0x1D, 0x29, 0xC5, 0x89, 0x6F, 0xB7, 0x62, 0x0E,
            0xAA, 0x18, 0xBE, 0x1B, 0xFC, 0x56, 0x3E, 0x4B, 0xC6, 0xD2, 0x79,
            0x20, 0x9A, 0xDB, 0xC0, 0xFE, 0x78, 0xCD, 0x5A, 0xF4, 0x1F, 0xDD,
            0xA8, 0x33, 0x88, 0x07, 0xC7, 0x31, 0xB1, 0x12, 0x10, 0x59, 0x27,
            0x80, 0xEC, 0x5F, 0x60, 0x51, 0x7F, 0xA9, 0x19, 0xB5, 0x4A, 0x0D,
            0x2D, 0xE5, 0x7A, 0x9F, 0x93, 0xC9, 0x9C, 0xEF, 0xA0, 0xE0, 0x3B,
            0x4D, 0xAE, 0x2A, 0xF5, 0xB0, 0xC8, 0xEB, 0xBB, 0x3C, 0x83, 0x53,
            0x99, 0x61, 0x17, 0x2B, 0x04, 0x7E, 0xBA, 0x77, 0xD6, 0x26, 0xE1,
            0x69, 0x14, 0x63, 0x55, 0x21, 0x0C, 0x7D};

    public AES(byte[] originalKey) {
        AES.originalKey = originalKey;
        if(originalKey.length == 16) {
            Nk = 4;
            Nb = 4;
            Nr = 10;
        }
        else if(originalKey.length == 24) {
            Nk = 6;
            Nb = 4;
            Nr = 12;
        }
        else if(originalKey.length == 32) {
            Nk = 8;
            Nb = 4;
            Nr = 14;
        }
    }

    private byte[][] subBytes(byte[][] bytes) {
        byte[][] result = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int element = bytes[i][j] & 0xff;
                result[i][j] = (byte) sbox[element];
            }
        }
        return result;
    }

    private byte[][] inverseSubBytes(byte [][]bytes ) {
        byte[][] result = new byte[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int element = bytes[i][j] & 0xff;
                result[i][j] = (byte) inv_sbox[element];
            }
        }
        return result;
    }

    private void shiftRows(byte[][] bytes) {
        byte[] temp = new byte[4];
        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                temp[j] = bytes[i][(i + j) % 4];
            }
            System.arraycopy(temp, 0, bytes[i], 0, 4);
        }
    }

    private void invShiftRows(byte[][] bytes) {
        byte[] temp = new byte[4];
        for (int i = 1; i < 4; i++)
        {
            for (int j = 0; j < 4; j++)
                temp[(i + j)%4] = bytes[i][j];
            System.arraycopy(temp, 0, bytes[i], 0, 4);
        }
    }

    private void mixColumns(byte[][] s)
    {
        int[] sp = new int[4];
        byte b02 = (byte)2, b03 = (byte)3;
        for (int c = 0; c < 4; c++)
        {
            sp[0] = fMul(b02, s[0][c]) ^ fMul(b03, s[1][c]) ^ s[2][c]  ^ s[3][c];
            sp[1] = s[0][c]  ^ fMul(b02, s[1][c]) ^ fMul(b03, s[2][c]) ^ s[3][c];
            sp[2] = s[0][c]  ^ s[1][c]  ^ fMul(b02, s[2][c]) ^ fMul(b03, s[3][c]);
            sp[3] = fMul(b03, s[0][c]) ^ s[1][c]  ^ s[2][c]  ^ fMul(b02, s[3][c]);
            for (int i = 0; i < 4; i++) s[i][c] = (byte)(sp[i]);
        }
    }

    public  byte fMul(byte a, byte b)
    {
        byte aa = a, bb = b, r = 0, t;
        while (aa != 0)
        {
            //check if the lowest significant bit is 1
            if ((aa & 1) != 0)
            {
                r = (byte) (r ^ bb);
            }
            //80 is 128
            t = (byte) (bb & 0x80);
            bb = (byte) (bb << 1);
            //check if the most significant bit is 1
            if (t != 0)
            {
                bb = (byte) (bb ^ 0x1b);
            }
            aa = (byte)(aa >> 1);
        }
        return r;
    }

    private void invMixColumns(byte[][] s)  {
        int[] sp = new int[4];
        byte b02 = (byte)0x0e, b03 = (byte)0x0b, b04 = (byte)0x0d, b05 = (byte)0x09;
        for (int c = 0; c < 4; c++)
        {
            sp[0] = fMul(b02, s[0][c]) ^ fMul(b03, s[1][c]) ^ fMul(b04,s[2][c])  ^ fMul(b05,s[3][c]);
            sp[1] = fMul(b05, s[0][c]) ^ fMul(b02, s[1][c]) ^ fMul(b03,s[2][c])  ^ fMul(b04,s[3][c]);
            sp[2] = fMul(b04, s[0][c]) ^ fMul(b05, s[1][c]) ^ fMul(b02,s[2][c])  ^ fMul(b03,s[3][c]);
            sp[3] = fMul(b03, s[0][c]) ^ fMul(b04, s[1][c]) ^ fMul(b05,s[2][c])  ^ fMul(b02,s[3][c]);
            for (int i = 0; i < 4; i++) s[i][c] = (byte)(sp[i]);
        }
    }

    private byte[][] addRoundKey(byte[][] state, int round)
    {
        byte[][] tmp = new byte[4][4];
        int start = round*4;
        int end = start+4;
        int temp_x = 0;
        for (int c = start; c < end; c++)
        {
            for (int l = 0; l < 4; l++) {
                tmp[temp_x][l] = (byte) (state[temp_x][l] ^ keyForAllRounds[ c][l]);
            }
            temp_x++;
        }
        return tmp;
    }

    public static byte[][] generateKeyForAllRounds(byte[] key) {
        byte[][] roundKey = new byte[60][4];
        int rconIteration = 1;
        byte[] temp = new byte[4];
        //Now we have 16 bytes consist of key bytes
        int bytesGenerated = Nk * Nb;
        for (int i = 0; i < 16; i++) {
            roundKey[i / 4][i % 4] = key[i];
        }
        int expaned = (Nb * (Nr + 1) * 4);
        while (bytesGenerated < expaned) {
            // Every four times we apply the core schedule to transform the
            // previous 4-byte word into the next.
                //Now we have vector 4 bytes
                System.arraycopy(roundKey[(bytesGenerated / 4) - 1], 0, temp, 0, 4);
                temp = Rotate(temp);
                temp = SubBytes(temp);
                rcon(temp, rconIteration);
                getLast4BytesandXor(roundKey, temp, bytesGenerated);
                bytesGenerated += 4;

            // We XOR temp with the four-byte block 16 bytes before the new
            // block.  This becomes the next four bytes in the key.
            for (int i = 0; i < 3; i++) {
                System.arraycopy(roundKey[(bytesGenerated / 4) - 1], 0, temp, 0, 4);
                getLast4BytesandXor(roundKey, temp, bytesGenerated);
                bytesGenerated += 4;
            }
            if(originalKey.length == 32 && bytesGenerated < 240) {
                System.arraycopy(roundKey[(bytesGenerated / 4) - 1], 0, temp, 0, 4);
                temp = SubBytes(temp);
                getLast4BytesandXor(roundKey, temp, bytesGenerated);
                bytesGenerated += 4;
                for (int i = 0; i < 3; i++) {
                    System.arraycopy(roundKey[(bytesGenerated / 4) - 1], 0, temp, 0, 4);
                    getLast4BytesandXor(roundKey, temp, bytesGenerated);
                    bytesGenerated += 4;
                }
            }
            if (originalKey.length == 24 && bytesGenerated < 192) {
                for (int i = 0; i < 2; i++) {
                    System.arraycopy(roundKey[(bytesGenerated / 4) - 1], 0, temp, 0, 4);
                    getLast4BytesandXor(roundKey, temp, bytesGenerated);
                    bytesGenerated += 4;
                }
            }
            rconIteration++;
        }
        return roundKey;
    }

    private static void getLast4BytesandXor(byte[][]roundKey, byte[] temp, int byteGenerated) {
        // get the 4 last bytes for round key
        byte[] subKey = new byte[4];
        byte[]result;
        System.arraycopy(roundKey[(byteGenerated / 4) - 4], 0, subKey, 0, 4);
        // Wykonaj XOR 4-bajtowego wektora i 4-bajtowego bloku
        result = xorWords(subKey,temp);
        // Dopisz wynik na koniec rozszerzanego klucza
        assert result != null;
        System.arraycopy(result, 0, roundKey[(byteGenerated / 4) - 1], 0, 4);
    }
    public static byte[] xorWords(byte[] word1, byte[] word2) {
        if (word1.length == word2.length) {
            byte[] tmp = new byte[word1.length];
            for (int i = 0; i < word1.length; i++) {
                tmp[i] = (byte) (word1[i] ^ word2[i]);
            }
            return tmp;
        } else {
            return null;
        }
    }

    //This method applies to rotate transform to a word
    private static byte[] Rotate(byte[] in) {
        byte[] out = new byte[4];
        for(int i = 0; i<4; i++) {
            out[i] = in[(i+1)%4];
        }
        return out;
    }

    private static void rcon(byte[] in, int round) {
        int rcon_val = (int) Math.pow(2, round - 1);
        in[0] ^= (byte) (rcon_val);
    }

    //This method applies the subBytes transform to a word
    private static byte[] SubBytes(byte[] in) {
        byte []result  = new byte[4];
        for (int j = 0; j < 4; j++) {
            int element = in[j] & 0xff;
            result[j] = (byte) sbox[element];
        }
        return result;
    }

    //This method applies the core schedule to a word.
    private  byte  []encrypt(byte[] arr_bytes) {
        byte[] tmp = new byte[arr_bytes.length];
        byte[][] state = new byte[4][4];
        for (int i = 0; i < arr_bytes.length; i++)
            //save arr-bytes to two diamension state arr
            state[i / 4][i % 4] = arr_bytes[i];
        keyForAllRounds = generateKeyForAllRounds(originalKey);
        state = addRoundKey(state,0);
        for (int round = 1; round <= Nr-1; round++) {
            state = subBytes(state);
            shiftRows(state);
            mixColumns(state);
            state = addRoundKey(state,round);
        }
        state = subBytes(state);
        shiftRows(state);
        state = addRoundKey(state, Nr);
        for (int i = 0; i < tmp.length; i++)
            tmp[i] = state[i / 4][i%4];
        return tmp;
    }

    public byte[] divideBytesOn128bitsAndEncode(byte[]message) {
        byte[]blocks = new byte[16];
        //Create array with bites of message,but size=len is multiple 16
        int len;
        if(message.length/16 == 0) {
            len = 16;
        }
        else if (message.length%16 != 0) {
            len = ((message.length/16)+1)*16;
        }
        else {
            len = message.length;
        }
        byte [] multipleSizeOfBlockArray = new byte[len];
        //Add 0 to array if you need
        System.arraycopy(message, 0, multipleSizeOfBlockArray, 0, message.length);
        for(int j = message.length; j < len; j++) {
            multipleSizeOfBlockArray[j] = 0;
        }
        //Send block to encrypt function
        byte []encrypted = new byte [len];
        for(int i = 0; i < multipleSizeOfBlockArray.length/16; i++) {
            System.arraycopy(multipleSizeOfBlockArray, i*16, blocks, 0, 16);
            blocks = encrypt(blocks);
            System.arraycopy(blocks, 0, encrypted, i*16, 16);
        }
        return encrypted;
    }

    private byte  []decrypt(byte[] arr_bytes) {
        byte[] tmp = new byte[arr_bytes.length];
        byte[][] state = new byte[4][4];
        for (int i = 0; i < arr_bytes.length; i++)
            //save arr-bytes to two diamension state arr
            state[i / 4][i % 4] = arr_bytes[i];
        keyForAllRounds = generateKeyForAllRounds(originalKey);
        state=addRoundKey(state,Nr);
        for (int round = Nr-1; round >= 1; round--) {
            state = inverseSubBytes(state);
            invShiftRows(state);
            state = addRoundKey(state, round);
            invMixColumns(state);
        }
        state = inverseSubBytes(state);
        invShiftRows(state);
        state = addRoundKey(state, 0);
        for (int i = 0; i < tmp.length; i++)
            tmp[i] = state[i / 4][i%4];
        return tmp;

    }

    public byte []decode(byte[]encrypted_text) {
        byte[]blocks = new byte[16];
        byte []encrypted = new byte [encrypted_text.length];
        for(int i = 0; i < encrypted_text.length/16; i++) {
            System.arraycopy(encrypted_text, i*16, blocks, 0, 16);
            blocks = decrypt(blocks);
            System.arraycopy(blocks, 0, encrypted, i*16, 16);
        }
        int var = 0;
        for (int i = 15; i > 0; i--) {
            if (encrypted[i] == 0) {
                var += 1;
            } else {
                break;
            }
        }
        byte[] result = new byte[encrypted.length - var];
        System.arraycopy(encrypted, 0, result, 0, encrypted.length - var);
        return result;
    }
}