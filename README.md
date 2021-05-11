# Jogo de Dominó
Desenvolvido por: Fabricio Adriano da Cruz

Este repositório contém os arquivos de um jogo de Dominó desenvolvido na linguagem Java como parte de um trabalho para a disciplina de Estrutura de Dados.

A estrutura de dados utilizada para a construção deste jogo de dominó foi a lista duplamente encadeada. Seguindo o funcionamento dessa estrutura cada peça do dominó possui referência para a peça anterior a ela e referência para a posterior, desta forma fazendo um encadeamento entre as peças, semelhante ao funcionamento do dominó como conhecemos.
Este jogo de dominó possui dois participantes: o computador (IA) e um jogador (usuário). O programa ao ser iniciado cria quatro listas sendo elas representações do monte, das peças distribuídas ao computador e ao jogador e ainda conta com uma lista referente a mesa, que é onde serão inseridas as peças jogadas pelos participantes.

O algoritmo do programa distribui sete peças de maneira aleatória para cada participante. Após a distribuição ele realiza a primeira jogada de forma automática com base no critério de quem possuir a maior peça:
- O que se pode considerar de lados iguais. Por exemplo: (5 | 5), (6 | 6) ...
- Ou no caso de os participantes não possuam peças de lados iguais, o programa busca pela peça cuja soma de lados seja a maior.
Definida a primeira jogada de maneira automática, o algoritmo consegue controlar de quem é a vez através de uma variável lógica que muda de valor conforme se dá o andamento do jogo.

O programa conta com a funcionalidade de comprar peças do monte caso ambos os jogadores não possuam peças em mãos, para isso ao ser solicitada uma compra de peça, o algoritmo a distribui de maneira aleatória a partir do monte.

Durante o desenrolar do jogo, o algoritmo procura ter verificações a cada jogada dos participantes a fim de determinar se alguém conseguiu jogar todas as suas peças o que caracteriza o fim do jogo e vitória por parte deste participante.
O fim do jogo do jogo se dá das seguintes maneiras:
- Um dos participantes consegue jogar todas as peças que possuía;
- Ou caso o jogo se encontre em um estado que não existem peças a serem encaixadas na mesa ,isso considerando também que não existem mais peças disponíveis para a compra, o que leva o algoritmo a fazer uma soma total dos lados de cada peça de ambos os participantes e quem possuir somatório menor, vence o jogo. Ainda vale destacar que caso ambos possuam o mesmo somatório, o jogo é considerado um empate.
