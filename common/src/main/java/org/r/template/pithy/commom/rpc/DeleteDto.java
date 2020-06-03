package org.r.template.pithy.commom.rpc;

import java.util.List;

/**
 * date 2020/6/3 下午2:34
 *
 * @author casper
 **/
public class DeleteDto<T> {

    /**
     * 需要删除的id
     */
    private List<T> ids;

    /**
     * 是否逻辑删除
     */
    private boolean isLogical;


    public List<T> getIds() {
        return ids;
    }

    public void setIds(List<T> ids) {
        this.ids = ids;
    }

    public boolean isLogical() {
        return isLogical;
    }

    public void setLogical(boolean logical) {
        isLogical = logical;
    }
}
