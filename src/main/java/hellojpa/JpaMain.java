package hellojpa;


import jakarta.persistence.*;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        //persistence.xml에 있는 "hello" 가져다쓰기 위한 작업
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //Member findMember = em.find(Member.class, 1L);
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                            .setFirstResult(5) //페이징처리할 때
                            .setMaxResults(8) //페이징처리할 때
                            .getResultList();

            for(Member member : result){
                System.out.println(member.getName());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); // 닫아줘야 데이터베이스 컬랙션이 반환됨
        }
        emf.close();
    }
}
