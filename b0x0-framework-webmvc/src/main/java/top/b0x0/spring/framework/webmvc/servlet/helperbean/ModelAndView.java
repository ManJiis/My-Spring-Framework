package top.b0x0.spring.framework.webmvc.servlet.helperbean;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ModelAndView
 *
 * @author ManJiis
 * @since 2021-08-22
 * @since JDK1.8
 */
public class ModelAndView {

    /**
     * 页面路径
     */
    private String view;

    /**
     * 页面data数据
     */
    private Map<String, Object> model = new LinkedHashMap<>();

    public ModelAndView setView(String view) {
        this.view = view;
        return this;
    }

    public String getView() {
        return view;
    }

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        model.put(attributeName, attributeValue);
        return this;
    }

    public ModelAndView addAllObjects(Map<String, ?> modelMap) {
        model.putAll(modelMap);
        return this;
    }

    public Map<String, Object> getModel() {
        return model;
    }

}
