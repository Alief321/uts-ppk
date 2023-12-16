package com.uts.IPK_IPS_Mahasiswa.controller;

import com.uts.IPK_IPS_Mahasiswa.entity.Quotes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class QuotesController {
    private final List<Quotes> quotes;

    public QuotesController() {
        // Inisialisasi quotes
        quotes = new ArrayList<>();
        quotes.add(new Quotes("Usaha dan keberanian tidak cukup tanpa tujuan dan arah perencanaan.", "John F. Kennedy"));
        quotes.add(new Quotes("Hal yang paling penting adalah menikmati hidupmu, menjadi bahagia, apapun yang terjadi.", "Audrey Hepburn"));
        quotes.add(new Quotes("Dunia ini cukup untuk memenuhi kebutuhan manusia, bukan untuk memenuhi keserakahan manusia.", "Mahatma Gandhi"));
        quotes.add(new Quotes("Semua impian kita bisa menjadi kenyataan, jika kita memiliki keberanian untuk mengejarnya.", "Walt Disney"));
        quotes.add(new Quotes("Rahasia untuk maju adalah memulai.", "Mark Twain"));
        quotes.add(new Quotes("Kita harus menerima bahwa kita tidak akan selalu membuat keputusan yang tepat, bahwa kita kadang-kadang akan mengacaukannya – memahami bahwa kegagalan bukanlah lawan dari kesuksesan, itu adalah bagian dari kesuksesan.", "Arianna Huffington"));
        quotes.add(new Quotes("Hidup itu bukan soal menemukan diri Anda sendiri, hidup itu membuat diri Anda sendiri.", "George Bernard Shaw"));
        quotes.add(new Quotes("Hidup adalah mimpi bagi mereka yang bijaksana, permainan bagi mereka yang bodoh, komedi bagi mereka yang kaya, dan tragedi bagi mereka yang miskin.", " Sholom Aleichem"));
        quotes.add(new Quotes("Seseorang yang berani membuang satu jam waktunya tidak mengetahui nilai dari kehidupan.", "Charles Darwin"));
        quotes.add(new Quotes("Satu-satunya keterbatasan dalam hidup adalah perilaku yang buruk.", "Scott Hamilton"));
        quotes.add(new Quotes("Orang yang lemah tidak mampu memaafkan. Memaafkan adalah ciri orang yang kuat.", "Mahatma Gandhi"));
        quotes.add(new Quotes("Hiduplah seolah engkau mati besok. Belajarlah seolah engkau hidup selamanya.", "Mahatma Gandhi"));
        quotes.add(new Quotes("Pendidikan adalah satu-satunya kunci untuk membuka dunia ini, serta paspor untuk menuju kebebasan.", "Oprah Winfrey"));
        quotes.add(new Quotes("Untuk sukses, sikap sama pentingnya dengan kemampuan.", "Walter Scott"));
        quotes.add(new Quotes("Yang membuatku terus berkembang adalah tujuan-tujuan hidupku.", "Muhammad Ali"));
        
        // Tambahkan quotes lainnya sesuai kebutuhan
    }

    @GetMapping("/quotes")
    public Quotes getRandomQuote() {
        Random random = new Random();
        int randomIndex = random.nextInt(quotes.size());
        return quotes.get(randomIndex);
    }
}