export interface Conta {
    id?: string;
    nome: string,
    valorOriginal: DoubleRange,
    valorCorrigido: DoubleRange,
    dataVencimento: string,
    dataPagamento: string,
    diasAtrasado: number,
    status: string,
    ativo: boolean
}