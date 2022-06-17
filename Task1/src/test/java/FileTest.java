import files.File;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Field;

public class FileTest {
    public final String SIZE_EXCEPTION = "Wrong size";
    public final String NAME_EXCEPTION = "Wrong name";
    public final String SPACE_STRING = " ";
    public final String FILE_PATH_STRING = "@D:\\JDK-intellij-downloader-info.txt";
    public final String CONTENT_STRING = "Some text";

    public double lenght;

    /* ПРОВАЙДЕР */
    @DataProvider(name = "filesData")
    public Object[][] setFileList(){
        return new Object[][] { {new File(FILE_PATH_STRING, CONTENT_STRING), FILE_PATH_STRING, CONTENT_STRING},
                {new File(SPACE_STRING, SPACE_STRING), SPACE_STRING, SPACE_STRING} };
    }

    /* Тестируем получение размера */
    @Test (dataProvider = "filesData")
    public void testGetSize(File newFile, String name, String content) {
        lenght = content.length() / 2;
        Assert.assertEquals(newFile.getSize(), lenght, SIZE_EXCEPTION);
    }

    /* Тестируем получение имени */
    @Test (dataProvider = "filesData")
    public void testGetFilename(File newFile, String name, String content) {
        Assert.assertEquals(newFile.getFilename(), name, NAME_EXCEPTION);
    }

    // Тестирование корректности определения расширения файла в конструкторе
    @Test(dataProvider = "filesData")
    public void testExtensionDetecting(File file, String name, String content) {
        String extension = name.split("\\.")[name.split("\\.").length - 1];
        try {

            Field field = File.class.getDeclaredField("extension");
            field.setAccessible(true);
            Assert.assertEquals(field.get(file), extension);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}