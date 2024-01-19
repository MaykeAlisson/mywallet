package com.mywallet.api.controllers.impl;

import com.mywallet.api.controllers.ActiveController;
import com.mywallet.api.domain.dto.ActiveDto;
import com.mywallet.api.domain.dto.ActiveUpdateDto;
import com.mywallet.api.domain.entity.Active;
import com.mywallet.api.provider.authentication.PreAuthorizeProvider;
import com.mywallet.api.services.ActiveService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Set;

@RestController
@AllArgsConstructor
public class ActiveControllerImpl implements ActiveController {

    private final ActiveService activeService;

    @Override
    @PreAuthorize(PreAuthorizeProvider.IS_AUTHENTICATED)
    public ResponseEntity<Active> create(ActiveDto dto) {
        var active = this.activeService.create(dto);
        final URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(active.getId()).toUri();
        return ResponseEntity.created(uri).body(active);
    }

    @Override
    @PreAuthorize(PreAuthorizeProvider.IS_AUTHENTICATED)
    public ResponseEntity<Active> update(final Long id, final ActiveUpdateDto dto) {
        var active = this.activeService.update(id, dto);
        return ResponseEntity.ok().body(active);
    }

    @Override
    @PreAuthorize(PreAuthorizeProvider.IS_AUTHENTICATED)
    public ResponseEntity<Set<Active>> findAll() {
        var actives = this.activeService.findAll();
        return ResponseEntity.ok().body(actives);
    }

    @Override
    public ResponseEntity<Void> delete(final Long id) {
        this.activeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
