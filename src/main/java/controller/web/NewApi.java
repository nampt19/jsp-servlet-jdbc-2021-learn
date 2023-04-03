package controller.web;


import model.NewModel;
import org.codehaus.jackson.map.ObjectMapper;
import pageAndSort.PageRequest;
import pageAndSort.Pageable;
import pageAndSort.Sorter;
import service.INewService;
import Util.HttpUtil;
import Util.SessionUtil;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet("/api/new")
public class NewApi extends HttpServlet {
    @Inject
    INewService newService;

    ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        Pageable pageable;

        Sorter sorter = null;
        if (req.getParameter("sort") != null) {
            String[] sortStr = req.getParameter("sort").split("-");
            sorter = new Sorter(sortStr[0], sortStr[1]);
        }

        pageable = new PageRequest(
                req.getParameter("page") == null ? null : new Integer(req.getParameter("page")),
                req.getParameter("limit") == null ? null : new Integer(req.getParameter("limit")),
                sorter);

        List<NewModel> list = newService.findAll(pageable);
        mapper.writeValue(resp.getOutputStream(), list);
        System.out.println("SessionLogin2: "+(String) SessionUtil.getInstance().getValue(req,"ADMIN"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        NewModel news = HttpUtil.of(req.getReader()).toModel(NewModel.class);

        news.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        news.setCreatedBy((String) SessionUtil.getInstance().getValue(req,"ADMIN"));

        NewModel model = newService.insert(news);
        mapper.writeValue(resp.getOutputStream(), model);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        NewModel news = HttpUtil.of(req.getReader()).toModel(NewModel.class);

        news.setModifiedDate(new Timestamp(System.currentTimeMillis()));

        news.setModifiedBy((String) SessionUtil.getInstance().getValue(req,"ADMIN"));

        NewModel model = newService.update(news);
        mapper.writeValue(resp.getOutputStream(), model);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        NewModel news = HttpUtil.of(req.getReader()).toModel(NewModel.class);
        newService.delete(news.getId());
        mapper.writeValue(resp.getOutputStream(), "{}");
    }
}
