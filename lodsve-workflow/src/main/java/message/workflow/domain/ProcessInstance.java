package message.workflow.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 流程实例.
 *
 * @author sunhao(sunhao.java@gmail.com)
 * @version V1.0, 16/4/15 下午4:12
 */
@Entity
@Table(name = "t_process_instance")
public class ProcessInstance {
    /**
     * 流程实例ID
     */
    @Id
    private Long id;
    /**
     * 流程ID
     */
    @Column
    private Long flowId;
    /**
     * 流程名
     */
    @Column
    private String flowTitle;
    /**
     * 资源Id,业务Id
     */
    @Column
    private Long resourceId;
    /**
     * 流程实例的title
     */
    @Column
    private String processTitle;
    /**
     * 发起人
     */
    @Column
    private Long startUserId;
    /**
     * 发起人姓名
     */
    @Column
    private String startUserName;
    /**
     * 当前节点id
     */
    @Column
    private Long currentNodeId;
    /**
     * 当前节点名
     */
    @Column
    private String currentNodeTitle;
    /**
     * 发起时间
     */
    @Column
    private Date startTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFlowId() {
        return flowId;
    }

    public void setFlowId(Long flowId) {
        this.flowId = flowId;
    }

    public String getFlowTitle() {
        return flowTitle;
    }

    public void setFlowTitle(String flowTitle) {
        this.flowTitle = flowTitle;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getProcessTitle() {
        return processTitle;
    }

    public void setProcessTitle(String processTitle) {
        this.processTitle = processTitle;
    }

    public Long getStartUserId() {
        return startUserId;
    }

    public void setStartUserId(Long startUserId) {
        this.startUserId = startUserId;
    }

    public String getStartUserName() {
        return startUserName;
    }

    public void setStartUserName(String startUserName) {
        this.startUserName = startUserName;
    }

    public Long getCurrentNodeId() {
        return currentNodeId;
    }

    public void setCurrentNodeId(Long currentNodeId) {
        this.currentNodeId = currentNodeId;
    }

    public String getCurrentNodeTitle() {
        return currentNodeTitle;
    }

    public void setCurrentNodeTitle(String currentNodeTitle) {
        this.currentNodeTitle = currentNodeTitle;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
}
