# Інструкція з запуску програми

**JSON файли знаходяться в каталозі src\main\resources**

# 1. Запуск через IDE (IntelliJ IDEA)

Відкрийте клас App.java
```
Натисніть кнопку Run ▷
```
Обов'язково додайте 2 аргументи: src/main/resources genre

Перший аргумент: src/main/resources

Другий аргумент: genre

Результат: Буде згенеровано файл statistics_by_genre.xml в каталозі **output**

# 2. Запуск через batch файл

Запустіть run.bat з кореневої папки проекту
```
run.bat
```
Результат: Буде згенеровано файл statistics_by_genre.xml в каталозі **output**

# 3. Запуск через JAR файл 

Запустіть програму
```
Проект має бути попередньо зібраний! Наприклад, за допомогою команди mvn package.
java -jar .\target\JavaCore-1.0-SNAPSHOT.jar .\src\main\resources producer
```
Результат: Буде згенеровано файл statistics_by_genre.xml в каталозі **output**

**Додаткова інформація
Інші доступні атрибути для аналізу:**

За продюсерами
java -jar JavaCore-1.0-SNAPSHOT.jar ..\src\main\resources producer

За роком випуску
java -jar JavaCore-1.0-SNAPSHOT.jar ..\src\main\resources date