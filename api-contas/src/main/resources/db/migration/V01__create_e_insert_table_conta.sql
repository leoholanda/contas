CREATE TABLE IF NOT EXISTS conta (
    id bigserial NOT NULL,
    nome VARCHAR(255) NOT NULL,
    valor_original DECIMAL(10, 2) NOT NULL,
    valor_corrigido DECIMAL(10, 2),
    data_vencimento DATE NOT NULL,
    data_pagamento DATE,
    quantidade_dias_atrasado INTEGER,
    status VARCHAR(30) NOT NULL,
    ativo BOOLEAN NOT NULL,
    primary key (id)
);

INSERT INTO conta (nome, valor_original, data_vencimento, status, ativo) values ('Taxa de Condomínio', 200, '2023-09-10', 'PENDENTE', true);
INSERT INTO conta (nome, valor_original, data_vencimento, status, ativo) values ('Conta de Energia', 152.45, '2023-09-01', 'PENDENTE', true);
INSERT INTO conta (nome, valor_original, data_vencimento, data_pagamento, status, ativo) values ('Conta de Água', 50, '2023-09-05', '2023-09-01', 'PAGO', true);
INSERT INTO conta (nome, valor_original, data_vencimento, status, ativo) values ('Mensalidade Escolar', 1000, '2023-09-02', 'PENDENTE', true);
