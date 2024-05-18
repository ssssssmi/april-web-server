package ru.otus.smi.web.server;

public class Main {
    public static void main(String[] args) {
        int port = Integer.parseInt((String) System.getProperties().getOrDefault("port", "8189"));
        new HttpServer(port).start();
    }
    // фронт, джар, логирование и параметризированный запуск сервера через консоль
}