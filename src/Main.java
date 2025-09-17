import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class DownloadTask implements Runnable {
    private final String fileName;

    public DownloadTask(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void run() {
        System.out.println("Iniciando download de: " + fileName + " - Thread: " + Thread.currentThread().getName());
        try {
            // Simula tempo de download (entre 1 e 3 segundos)
            int tempo = (int) (Math.random() * 2000 + 1000);
            Thread.sleep(tempo);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Download concluído de: " + fileName + " ✅");
    }
}

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(3);

        String[] arquivos = {"musica.mp3", "video.mp4", "documento.pdf", "imagem.png", "planilha.xlsx"};

        for (String arquivo : arquivos) {
            executor.submit(new DownloadTask(arquivo));
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }

        System.out.println("Todos os downloads foram finalizados!");
    }
}
