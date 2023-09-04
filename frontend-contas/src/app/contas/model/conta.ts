export interface Conta {
    id?: string;
    nome: string,
    valorOriginal: DoubleRange,
    valorCorrigido: DoubleRange,
    dataVencimento: Date,
    dataPagamento: Date,
    diasAtrasado: number,
    status: string,
    ativo: boolean
}