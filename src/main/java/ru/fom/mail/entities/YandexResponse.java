package ru.fom.mail.entities;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YandexResponse {

    @SerializedName("domain") private String domain;
    @SerializedName("page") private int page;
    @SerializedName("pages") private int pages;
    @SerializedName("on_page") private int onPage;
    @SerializedName("total") private int total;
    @SerializedName("found") private int found;
    @SerializedName("accounts") private List<YandexUser> accounts;
    @SerializedName("status") private String status;


    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getOnPage() {
        return onPage;
    }

    public void setOnPage(int onPage) {
        this.onPage = onPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getFound() {
        return found;
    }

    public void setFound(int found) {
        this.found = found;
    }

    public List<YandexUser> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<YandexUser> accounts) {
        this.accounts = accounts;
    }

    public boolean isSuccessful() {
        return status.equals("ok");
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
