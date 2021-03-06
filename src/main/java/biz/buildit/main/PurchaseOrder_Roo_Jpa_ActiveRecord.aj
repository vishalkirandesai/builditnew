// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package biz.buildit.main;

import biz.buildit.main.PurchaseOrder;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect PurchaseOrder_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager PurchaseOrder.entityManager;
    
    public static final EntityManager PurchaseOrder.entityManager() {
        EntityManager em = new PurchaseOrder().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long PurchaseOrder.countPurchaseOrders() {
        return entityManager().createQuery("SELECT COUNT(o) FROM PurchaseOrder o", Long.class).getSingleResult();
    }
    
    public static List<PurchaseOrder> PurchaseOrder.findAllPurchaseOrders() {
        return entityManager().createQuery("SELECT o FROM PurchaseOrder o", PurchaseOrder.class).getResultList();
    }
    
    public static PurchaseOrder PurchaseOrder.findPurchaseOrder(Long id) {
        if (id == null) return null;
        return entityManager().find(PurchaseOrder.class, id);
    }
    
    public static List<PurchaseOrder> PurchaseOrder.findPurchaseOrderEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM PurchaseOrder o", PurchaseOrder.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void PurchaseOrder.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void PurchaseOrder.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            PurchaseOrder attached = PurchaseOrder.findPurchaseOrder(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void PurchaseOrder.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void PurchaseOrder.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public PurchaseOrder PurchaseOrder.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        PurchaseOrder merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
