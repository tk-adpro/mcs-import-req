package id.ac.ui.cs.advprog.eshop.mcsimportreq.repository;

import id.ac.ui.cs.advprog.eshop.mcsimportreq.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    // Metode untuk mendapatkan semua request
    @Override
    List<Request> findAll();

    // Metode untuk menyimpan atau update request
    @Override
    <S extends Request> S save(S entity);

    // Metode untuk mencari request berdasarkan ID
    @Override
    Optional<Request> findById(Long requestId);

    // Metode untuk menghapus request berdasarkan ID
    @Override
    void deleteById(Long requestId);
}
