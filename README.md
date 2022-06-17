# Исправленные тесты
1. FileStorageTest.testDelete  
Из дата провайдера удалено имя файла, который не существует в тестируемом экземпляре FileStorage.  
2. FileStorageTest.testGetFile  
Добавлена зависимость от теста testWrite.  
3. FileStorageTest.testIsExists  
Группа сменена на testWriteFunctions, добавлена зависимость от теста testWrite, assertFalse заменён на assertTrue.  
4. FileStorageTest.testGetFile  
Добавлена зависимость от теста testWrite.  
# Написанные тесты
1. FileTest.testExtensionDetecting  
Тестирует правильность определения расширения файла по его полному имени.  
2. FileStorageTest.testDeleteFileNotInStorage  
Тестирует удаление несуществующего в хранилище файла.  
3. FileStorageTest.testIsExistsNotExists  
Осуществляет проверку существования файла в хранилище для несуществующего файла.

