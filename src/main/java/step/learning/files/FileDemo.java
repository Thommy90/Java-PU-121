package step.learning.files;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.attribute.*;
import java.util.Scanner;
import java.nio.file.Files;


public class FileDemo {
    public void run(){
      //  System.out.println("Files");
      //  ioDemo();
       // dirDemo();
        dirInfo();
    }

    /**
    * Демонстрация работы с файловой системой
     */
    private void dirDemo(){                 // File ()java.io - основной класс для работы как файлами,
        File currentDir =                   // так и с папками. Создрание new Filе не впливае на ОС,
                new File("/Users/thommy/Downloads");    // а только создает обьект
        System.out.printf("File '%s'", currentDir.getName());
        if (currentDir.exists()){
            System.out.printf("exist %n" ) ;      // %n -> std:: endl, \n -> один символ
        }
        else {
            System.out.printf("does not exist %n" ) ;
        }

        if(currentDir.isDirectory()){
            System.out.println("Is Directory");
            File[] files = currentDir.listFiles();
            if(files != null)
            {
                for(File file : files){
                    System.out.printf(
                            "%s\t%s %n",
                            file.getName(),
                            file.isDirectory() ? "<DIR>" : "file"
                    );
                }
            }
        }
        else{
            System.out.printf("Is File: %s%s%s %n",
                    currentDir.canRead() ? "r" : "R",     // 1  1
                    currentDir.canWrite() ? "w" : "W",    // 1  0
                    currentDir.canExecute() ? "x" : "X"   // 1  1
                    );                                    // 7  5
        }
    }

    /**
     * Демонстранция ввода/вывода с файлами
     */
    private void ioDemo(){
        String fileContent = "This is content of a file\n" +
                "This is a new line";
        String filename = "test-file.txt" ;
        try ( FileWriter writer =  new FileWriter( filename) ) {
            writer.write(fileContent);
            System.out.println("Write success");
        } catch (IOException ex) {
            System.err.println(ex.getMessage() );
        }
        System.out.println("Reading....");
        try(FileReader reader = new FileReader(filename);
            Scanner scanner = new Scanner (reader))
        {
            while( scanner.hasNext() ) {
                System.out.println(scanner.nextLine());
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage() );
        }
    }
    private void dirInfo(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter directory for scanning: ");
        File currentDir = new File(scanner.nextLine());
        String fileContent = "";
        if(currentDir.isDirectory()){
            File[] files = currentDir.listFiles();
            if(files != null)
            {
                for(File file : files) {
                    PosixFileAttributes attr = null;
                    try {
                        Files.setPosixFilePermissions(file.toPath(), PosixFilePermissions.fromString("rwxrwxrwx"));
                        attr = Files.getFileAttributeView(file.toPath(), PosixFileAttributeView.class).readAttributes();
                        assert attr != null;
                        System.out.printf(
                                "%s\t%s\t%s\t%s %n",
                                attr.creationTime().toString().replace('T', ' ').replace('Z', ' '),
                                file.isDirectory() ? "<DIR>" : "\t",
                                file.isDirectory() ? "\t" : attr.size(),
                                file.getName()
                        );
                        String Content =  attr.creationTime().toString().replace('T', ' ').replace('Z', ' ') + "\t" +
                                (file.isDirectory() ? "<DIR>\t" : "\t") + ( file.isDirectory() ? "\t" : attr.size()) + "\t" + file.getName() + "\n";
                        fileContent += Content;

                    } catch (IOException e) {
                        System.err.println(e.getMessage());
                    }
                }

            }
        }
        else{
            PosixFileAttributes attr = null;
            try {
                Files.setPosixFilePermissions(currentDir.toPath(), PosixFilePermissions.fromString("rwxrwxrwx"));
                attr = Files.getFileAttributeView(currentDir.toPath(), PosixFileAttributeView.class).readAttributes();
                assert attr != null;
                System.out.printf(
                        "%s\t%s\t%s %n",
                        attr.creationTime().toString().replace('T', ' ').replace('Z', ' '),
                        attr.size(),
                        currentDir.getName()
                );
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
             fileContent = attr.creationTime().toString().replace('T', ' ').replace('Z', ' ') + "\t" +
                    attr.size() + "\t" + currentDir.getName() + "\n";
        }
        try ( FileWriter writer =  new FileWriter("dir.txt") ) {
            writer.write(fileContent);
            System.out.println("Write success");
        } catch (IOException ex) {
            System.err.println(ex.getMessage() );
        }
    }

}

/* Работа с файлами
разделяется на 2 группы:
1. Работа с файловой системой - копирование, удаление, создание файлов, поиск и тд
2. Использование файлов для сохранение данных

1. Смотри dirDemo()
2. Особенность Java - присутствие большого количества засобов работы с потоками (stream)
InputStream - абстракция,которая обьеденяет все "читальные" классы
FileReader - потоковое читание по символах
FileInputStream - --//-- по байтам
BufferedReader - "оболочка", которая создает промежуточный буфер, который уменьшет количество
прямых операций читания с потока
Scanner - оболочка для читания разных типов данных.

OutputStream
FileWriter
FileOutputStream
BufferedWriter
PrintWriter - оболочка, которая дает способы формтированной печати (переводит разные типы данных
в символы для потока)

(int32)127 ->
(bin) 01111111 00000000 00000000 00000000
(txt) 00110001 00110010 00110111
         1         2        7

try-with-resource
try( Resource red = new Resource() ) { -- вместо using (C#) -- Auto Closable
}
catch() {
}
 */