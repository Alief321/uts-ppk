package com.uts.IPK_IPS_Mahasiswa.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Quotes {
    private String q;
    private String a;

    public Quotes(String text, String author) {
        this.q = text;
        this.a = author;
    }
}
