package com.arbol.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Fecha {

    private static final String[] MESES = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };

    public static Date fromIsoDateTime(String isoDateTime) {
        LocalDateTime localDateTime = LocalDateTime.parse(isoDateTime);
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date fromIsoDate(String isoDate) {
        return fromIsoDateTime(isoDate + "T00:00:00");
    }

    public static String todayString() {
        return dateString(LocalDate.now());
    }

    public static String dateString(LocalDate date) {
        int day = date.getDayOfMonth();
        int month = date.getMonthValue();
        int year = date.getYear();
        return (day > 1 ? day : "1°") + " de " + MESES[month - 1] + " del " + year;
    }

    public static String fechaDocumento() {
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd");
        return fechaActual.format(formatter);
    }

}