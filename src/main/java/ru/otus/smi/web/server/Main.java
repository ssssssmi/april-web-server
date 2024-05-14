package ru.otus.smi.web.server;

public class Main {
    // Домашнее задание:
    // - Добавить логирование (с правильным выбором уровня логирования для сообщений)
    // - Сделайте так, чтобы Request по методу понимал имеет ли смысл вообще искать body в запросе (в GET запросе body не должно быть)
    // - * При получении PUT /products обновите данные продукта
    // PUT:
    // {
    //   "id": "4b798830-d2ad-4ee1-b4b9-03866cb75596",
    //   "title": "new-name",
    //   "price": 1
    // }
    // У продукта с id = 4b798830-d2ad-4ee1-b4b9-03866cb75596 поля должны быть изменены на те значения, что пришли в теле PUT запроса

    public static void main(String[] args) {
        int port = Integer.parseInt((String)System.getProperties().getOrDefault("port", "8189"));
        new HttpServer(port).start();
    }

    // фронт, джар, логирование и параметризированный запуск сервера через консоль
}