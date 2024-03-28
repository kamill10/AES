package com.example.widok;

import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import org.example.krypto.AES;
import org.example.krypto.Key;
import java.io.*;
import java.util.HexFormat;


public class HelloController {
    public Button keyGenerator;
    public TextField key;
    public TextArea tekst_zaszyfrowany;
    public TextArea tekst_jawny;
    public Button jawny;
    public Button szyfr;
    public Button klucz;
    public RadioButton key128;
    public RadioButton key192;
    public RadioButton key256;
    Key generator = new Key();
    private byte [] dane = new byte[0];
    private byte [] kluczyk;

    public String converter(byte [] bytes){
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString();
    }
    public void setKey() {
        if(key128.isSelected()) {
            kluczyk = generator.keyGenerator(16);
        }
        else if (key192.isSelected()) {
            kluczyk = generator.keyGenerator(24);
        } else if (key256.isSelected()) {
            kluczyk = generator.keyGenerator(32);
        }
        else {
            System.out.println("Zaznacz rozmiar klucza!");
        }
        key.setText(converter(kluczyk));
    }

    public String getTekst_jawny() {
        return tekst_jawny.getText();
    }

    public String getKey() {
        return key.getText();
    }

    public String getTekst_zaszyfrowany() {
        return tekst_zaszyfrowany.getText();
    }

    public void setEncrypt() {
        AES aes = new AES(HexFormat.of().parseHex(getKey()));
        byte[] arr = aes.divideBytesOn128bitsAndEncode(getTekst_jawny().getBytes());
        tekst_zaszyfrowany.setText(converter(arr));
    }
    public void setDecrypt() {
        AES aes = new AES(HexFormat.of().parseHex(getKey()));
        byte[] decrypt = aes.decode(HexFormat.of().parseHex(getTekst_zaszyfrowany()));
        tekst_jawny.clear();
        tekst_jawny.setText(new String(decrypt));
    }
    public void zapis_do_pliku() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapis do pliku");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Wszystkie pliki", "*.*"));
        File fileToSave = fileChooser.showSaveDialog(Scene.getStage());
        if (fileToSave != null) {
            try {
                OutputStream fos = new FileOutputStream(fileToSave);
                fos.write(dane);
                fos.close();
                System.out.println("Zapisano pomyślnie!");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Zapis został przerwany");
        }
    }
    public byte[] odczyt_z_pliku() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Odczyt z pliku");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Wszystkie plik", "*.*"));
        File loadFile = fileChooser.showOpenDialog(Scene.getStage());
        if (loadFile != null) {
            try {
                FileInputStream fis = new FileInputStream(loadFile);
                int size = fis.available();
                dane = new byte [size];
                int bytes_read = fis.read(dane);
                if(bytes_read == 0) {
                    System.out.println("Plik jest pusty!");
                }
                else {
                    System.out.println("Odczytano pomyślnie!");
                }
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Odczyt został przerwany");
        }
        return dane;
    }
    public void saveKey() {
        dane = HexFormat.of().parseHex(getKey());
        zapis_do_pliku();
    }
    public void saveTextJ() {
        if(dane.length != 0)
            dane = HexFormat.of().parseHex(getTekst_jawny());
        else
            dane =  HexFormat.of().parseHex(converter(getTekst_jawny().getBytes()));
        zapis_do_pliku();
        dane = new byte[0];
    }
    public void saveTextZ() {
        dane = HexFormat.of().parseHex(getTekst_zaszyfrowany());
        zapis_do_pliku();
    }
    public void loadKey() {
        key.setText(converter(odczyt_z_pliku()));
    }
    public void loadTextJ() {
        tekst_jawny.setText(converter(odczyt_z_pliku()));
    }
    public void loadTextZ() {
        tekst_zaszyfrowany.setText(converter(odczyt_z_pliku()));
    }
}