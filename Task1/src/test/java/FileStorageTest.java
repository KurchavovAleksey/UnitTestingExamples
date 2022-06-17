import exception.FileNameAlreadyExistsException;
import files.File;
import files.FileStorage;
import org.testng.Assert;
import org.testng.annotations.*;

import java.lang.reflect.Field;

public class FileStorageTest {

    public final String MAX_SIZE_EXCEPTION = "DIFFERENT MAX SIZE";
    public final String NULL_FILE_EXCEPTION = "NULL FILE";
    public final String SPACE_STRING = " ";
    public final String FILE_PATH_STRING = "@D:\\JDK-intellij-downloader-info.txt";
    public final String CONTENT_STRING = "Some text";
    public final String REPEATED_STRING = "AA";
    public final String WRONG_SIZE_CONTENT_STRING = "TEXTtextTEXTtextTEXTtextTEXTtextTEXTtextTEXTtext" +
            "TEXTtextTEXTtextTEXTtextTEXTtextTEXTtext" +
            "TEXTtextTEXTtextTEXTtextTEXTtextTEXTtext" +
            "TEXTtextTEXTtextTEXTtextTEXTtextTEXTtext" +
            "TEXTtextTEXTtextTEXTtextTEXTtextTEXTtext";
    public final String TIC_TOC_TOE_STRING = "tictoctoe.game";

    public final int NEW_SIZE = 5;
    public final int TWENTY_ONE = 21;
    public final int FIFTY = -50;
    public final int ZERO = 0;

    public FileStorage storage;
    private final Object[][] files = new Object[][] {
                {new File(REPEATED_STRING, CONTENT_STRING)},
                {new File(SPACE_STRING, WRONG_SIZE_CONTENT_STRING)},
                {new File(FILE_PATH_STRING, CONTENT_STRING)}
        };

    @BeforeTest
    public void setUp() {
        storage = new FileStorage(NEW_SIZE);
    }

    /* Метод, выполняемый перед группами */
    @BeforeGroups(groups = "testExistFunction")
    public void setNewStorage() {
        storage = new FileStorage();
    }

    @BeforeGroups(groups = "testWriteFunctions")
    public void setNewStorageWrite() {
        storage = new FileStorage(13);
    }

    /* ПРОВАЙДЕРЫ */
    @DataProvider(name = "testSizeData")
    public Object[][] newData() {
        return new Object[][]{{TWENTY_ONE}, {FIFTY}, {ZERO}};
    }

    @DataProvider(name = "testFilesForStorage")
    public Object[][] newFiles() {
        return files;
    }

    @DataProvider(name = "testFilesForDelete")
    public Object[][] filesForDelete() {
        return new Object[][] { {REPEATED_STRING}};
    }

    @DataProvider(name = "nullExceptionTest")
    public Object[][] dataNullExceptionTest(){
        return new Object[][] { {new File (null, null)}, {null} };
    }

    @DataProvider(name = "testFileForException")
    public Object[][] newExceptionFile() {
        return new Object[][] { {new File(REPEATED_STRING, CONTENT_STRING)} };
    }

    /* Тестирование конструктора – рефлексия */
    @Test(dataProvider = "testSizeData")
    public void testFileStorage(int size) {
        try {
            storage = new FileStorage(size);

            Field field = FileStorage.class.getDeclaredField("maxSize");
            field.setAccessible(true);
            Assert.assertEquals( (int) field.getDouble(storage), size, MAX_SIZE_EXCEPTION );
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /* Тестирование записи файла */
    @Test (dataProvider = "testFilesForStorage", groups = "testWriteFunctions")
    public void testWrite(File file) throws FileNameAlreadyExistsException {
        Assert.assertTrue(storage.write(file));
    }

    /* Тестирование записи дублирующегося файла */
    @Test (dataProvider = "testFileForException", expectedExceptions = FileNameAlreadyExistsException.class)
    public void testWriteException(File file) throws FileNameAlreadyExistsException {
        Assert.assertTrue(storage.write(file));
    }

    /* Тестирование проверки существования файла */
    @Test (dataProvider = "testFilesForStorage", groups = "testWriteFunctions", dependsOnMethods = "testWrite")
    public void testIsExists(File file) {
        String name = file.getFilename();
        Assert.assertTrue(storage.isExists(name));
        try {
            storage.write(file);
        } catch (FileNameAlreadyExistsException e) {
            e.printStackTrace();
        }
    }

    /* Тестирование удаления файла */
    @Test (dataProvider = "testFilesForDelete", dependsOnMethods = {"testFileStorage", "testIsExists"})
    public void testDelete(String fileName) {
        Assert.assertTrue(storage.delete(fileName));
    }

    /* Тестирование получения файлов */
    @Test
    public void testGetFiles(){
        for (File el: storage.getFiles()) {
            Assert.assertNotNull(el);
        }
    }

    /* Тестирование получения файла */
    @Test (dataProvider = "testFilesForStorage", dependsOnMethods = "testWrite")
    public void testGetFile(File file) {
        Assert.assertEquals(storage.getFile(file.getFilename()), file);
    }

    // Тестирование удаления файла, которого нет в хранилище
    @Test
    public void testDeleteFileNotInStorage() {
        Assert.assertFalse(storage.delete(TIC_TOC_TOE_STRING));
    }

    // Проверка существования несуществующего файла в хранилище
    @Test
    public void testIsExistsNotExists() {
        Assert.assertFalse(storage.isExists(TIC_TOC_TOE_STRING));
    }
}
