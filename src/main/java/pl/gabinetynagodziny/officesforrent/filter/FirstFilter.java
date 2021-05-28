package pl.gabinetynagodziny.officesforrent.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class FirstFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);//jest to metoda domyslna nie trzeba implementacji
        //zaraz po utworzniu instancji filtra
        //nie wykona sie jak rzuci servletexception
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //to metoda, ktora robi filtrowanie
        System.out.println("robi sie filtr");
        //tylko to zablokuje zadanie
        //jak chcemy, zeby kontynuowalo droge to musi
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();//w celu infomowania jak filter jest skonczony, kiedy skonczona metoda doFilter albo jakis timeout

    }
}
