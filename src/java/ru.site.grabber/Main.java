package ru.site.grabber;

import ru.site.grabber.entity.Site;
import ru.site.grabber.service.SiteGrabService;
import ru.site.grabber.service.SiteGrabServiceImpl;

import java.util.ArrayList;
import java.util.List;


public class Main {

    public static void main(String[] args) {

        SiteGrabService service = new SiteGrabServiceImpl();
        List<String> links = new ArrayList<>();
        links.add("http://www.amokb.ru/");
        links.add("http://www.astu.org/");
        links.add("http://government.ru/");
        List<Site> sites = service.createRootSite(links);
        int i = 0;
        for (Site site : sites) {
            Threader thread = new Threader(site,i);    //Создание потока
            thread.start();
            System.out.println("url ="+ site.getSiteUrl()+" thread "+i+" start");
            i++;
        }
    }

    static class Threader extends Thread {
        SiteGrabService service = new SiteGrabServiceImpl();
        Site mySite;
        int thnum;

        Threader(Site site, int threadNumber) {
            mySite = site;
            thnum = threadNumber;
        }

        @Override
        public void run()    //Этот метод будет выполнен в побочном потоке
        {
            List<Site> childs = null;
            childs = service.getUrlMultithread(mySite, 1,thnum);
            for (int i = 2; i < 5; i++) {
                childs = service.getUrl(childs, i,thnum);
            }
            System.out.println(thnum+") thread end");
        }
    }
}
