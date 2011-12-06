package org.imirsel.extractotron.dao.hibernate;

import java.util.List;

import org.imirsel.extractotron.dao.ExecutionContextDao;
import org.imirsel.extractotron.model.ExecutionContext;
import org.springframework.stereotype.Repository;


@Repository("executionDao")
public class ExecutionContextDaoHibernate extends GenericDaoHibernate<ExecutionContext, Long> implements ExecutionContextDao{

    /**
     * Constructor that sets the entity to SongCollection.class
     */
    public ExecutionContextDaoHibernate() {
        super(ExecutionContext.class);
    }
	
	public List<ExecutionContext> getExecutionContexts() {
        return getHibernateTemplate().find("from ExecutionContext u order by u.timeStarted");
	}
	

}
