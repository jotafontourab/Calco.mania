<script setup>
</script>

<template>
  <div class="announcement-bar">
    <div class="container container-flex">
      <span class="promo-text">✨ <strong>PROMO DA CARTELA:</strong> Leve 10 stickers por apenas R$ 25!</span>
      <span class="promo-hint desktop-only">Economize em cada unidade ao montar seu conjunto.</span>
    </div>
  </div>
</template>

<style scoped>
.announcement-bar {
  background-color: var(--color-leaf);
  color: var(--color-forest-dark);
  padding: 8px 0;
  font-size: 0.8125rem;
  text-align: center;
  border-bottom: 1px solid rgba(0,0,0,0.05);
}

.container-flex {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 12px;
}

.promo-text strong {
  font-weight: 700;
}

.promo-hint {
  opacity: 0.8;
  font-style: italic;
}

@media (max-width: 640px) {
  .desktop-only {
    display: none;
  }
  .announcement-bar {
    font-size: 0.75rem;
  }
}
</style>
