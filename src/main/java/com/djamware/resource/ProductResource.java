package com.djamware.resource;

import java.util.List;

import com.djamware.model.Product;
import com.djamware.repository.ProductRepository;

import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("user")
public class ProductResource {

    @Inject
    ProductRepository repository;

    @GET
    public List<Product> getAll() {
        return repository.listAll();
    }

    @GET
    @Path("/{id}")
    public Product getById(@PathParam("id") Long id) {
        return repository.findById(id);
    }

    @POST
    @Transactional
    public Product create(Product product) {
        repository.persist(product);
        return product;
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Product update(@PathParam("id") Long id, Product updated) {
        Product product = repository.findById(id);
        if (product == null) {
            throw new NotFoundException();
        }
        product.name = updated.name;
        product.description = updated.description;
        product.price = updated.price;
        return product;
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public void delete(@PathParam("id") Long id) {
        repository.deleteById(id);
    }
}
