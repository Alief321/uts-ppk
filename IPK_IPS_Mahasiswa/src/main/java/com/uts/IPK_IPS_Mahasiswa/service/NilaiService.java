package com.uts.IPK_IPS_Mahasiswa.service;

import com.uts.IPK_IPS_Mahasiswa.entity.Nilai;
import com.uts.IPK_IPS_Mahasiswa.enumeration.KategoriMatkul;
import org.springframework.stereotype.Service;

@Service
public class NilaiService {

    public float getNilaiangka(Nilai nilai) {
        float nilaiAngka;
        if (nilai.getNilai_UTS() == 0 || nilai.getNilai_UAS() == 0 || nilai.getNilai_Tugas() == 0) {
            nilaiAngka = 0;
        }
        if (nilai.getMataKuliah().getKategori() == KategoriMatkul.T) {
            nilaiAngka = (float) (0.35 * nilai.getNilai_UTS() + 0.35 * nilai.getNilai_UAS() + 0.3 * nilai.getNilai_Tugas());
        } else {
            nilaiAngka = (float) (0.3 * nilai.getNilai_UTS() + 0.3 * nilai.getNilai_UAS() + 0.3 * nilai.getNilai_Praktikum() + 0.1 * nilai.getNilai_Tugas());
        }
        return nilaiAngka;
    }

    public String getNilaiHuruf(float nilaiAngka) {
        String nilaiHuruf = "";
        if (nilaiAngka >= 85 && nilaiAngka <= 100) {
            nilaiHuruf = "A";
        } else {
            if (nilaiAngka >= 80) {
                nilaiHuruf = "A-";
            } else {
                if (nilaiAngka >= 75) {
                    nilaiHuruf = "B+";
                } else {
                    if (nilaiAngka >= 70) {
                        nilaiHuruf = "B";
                    } else {
                        if (nilaiAngka >= 65) {
                            nilaiHuruf = "C+";
                        } else {
                            if (nilaiAngka >= 60) {
                                nilaiHuruf = "C";
                            } else {
                                if (nilaiAngka >= 55) {
                                    nilaiHuruf = "D+";
                                } else {
                                    if (nilaiAngka > 0) {
                                        nilaiHuruf = "D";
                                    } else {
                                        nilaiHuruf = "E";
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return nilaiHuruf;
    }

    public float getBobot(String nilaiHuruf) {
        float bobot = 0;
        switch (nilaiHuruf) {
            case "A":
                bobot = 4;
                break;
            case "A-":
                bobot = (float) 3.75;
                break;
            case "B+":
                bobot = (float) 3.5;
                break;
            case "B":
                bobot = (float) 3;
                break;
            case "C+":
                bobot = (float) 2.5;
                break;
            case "C":
                bobot = (float) 2;
                break;
            case "D+":
                bobot = (float) 1.5;
                break;
            case "D":
                bobot = (float) 1;
                break;
            case "E":
                bobot = (float) 0;
                break;
            default:
                throw new AssertionError();
        }
        return bobot;
    }
}
