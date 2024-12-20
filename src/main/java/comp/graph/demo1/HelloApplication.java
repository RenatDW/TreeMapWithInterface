package comp.graph.demo1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import logic.TreeMap;

import java.util.List;
import java.util.Set;

public class HelloApplication extends Application {

    private TreeMap<String, String> treeMap = new TreeMap<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TreeMap Interface");

        // Основная панель
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        // Панель ввода для добавления или поиска
        HBox inputPane = new HBox(10);
        TextField keyField = new TextField();
        keyField.setPromptText("Введите ключ");
        TextField valueField = new TextField();
        valueField.setPromptText("Введите значение");
        Button addButton = new Button("Добавить");
        Button searchButton = new Button("Найти");
        inputPane.getChildren().addAll(keyField, valueField, addButton, searchButton);

        // Вывод результата
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPromptText("Результат будет отображаться здесь");

        // Кнопки для удаления и отображения данных
        HBox actionPane = new HBox(10);
        Button removeButton = new Button("Удалить");
        Button showKeysButton = new Button("Показать ключи");
        Button showValuesButton = new Button("Показать значения");
        actionPane.getChildren().addAll(removeButton, showKeysButton, showValuesButton);

        root.getChildren().addAll(inputPane, actionPane, outputArea);

        // Логика кнопок

        // Добавление элемента
        addButton.setOnAction(event -> {
            String key = keyField.getText();
            String value = valueField.getText();
            if (key.isEmpty() || value.isEmpty()) {
                outputArea.setText("Ошибка: ключ и значение не могут быть пустыми!");
                return;
            }
            treeMap.put(key, value);
            outputArea.setText("Добавлено: ключ = " + key + ", значение = " + value);
            keyField.clear();
            valueField.clear();
        });

        // Поиск элемента по ключу
        searchButton.setOnAction(event -> {
            String key = keyField.getText();
            if (key.isEmpty()) {
                outputArea.setText("Ошибка: ключ не может быть пустым!");
                return;
            }
            String value = treeMap.get(key);
            if (value != null) {
                outputArea.setText("Найдено: ключ = " + key + ", значение = " + value);
            } else {
                outputArea.setText("Ключ '" + key + "' не найден!");
            }
            keyField.clear();
        });

        // Удаление элемента
        removeButton.setOnAction(event -> {
            String key = keyField.getText();
            if (key.isEmpty()) {
                outputArea.setText("Ошибка: ключ не может быть пустым!");
                return;
            }
            String removedValue = treeMap.remove(key);
            if (removedValue != null) {
                outputArea.setText("Удалено: ключ = " + key + ", значение = " + removedValue);
            } else {
                outputArea.setText("Ключ '" + key + "' не найден для удаления!");
            }
            keyField.clear();
        });

        // Показ всех ключей
        showKeysButton.setOnAction(event -> {
            Set<String> keys = treeMap.keySet();
            if (keys.isEmpty()) {
                outputArea.setText("Ключи отсутствуют.");
            } else {
                outputArea.setText("Ключи: " + String.join(", ", keys));
            }
        });

        // Показ всех значений
        showValuesButton.setOnAction(event -> {
            List<String> values = treeMap.values();
            if (values.isEmpty()) {
                outputArea.setText("Значения отсутствуют.");
            } else {
                outputArea.setText("Значения: " + String.join(", ", values));
            }
        });

        // Настройка и отображение сцены
        Scene scene = new Scene(root, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}