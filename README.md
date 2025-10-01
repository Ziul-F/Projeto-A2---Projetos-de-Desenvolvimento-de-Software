# Projeto-A2---Projetos-de-Desenvolvimento-de-Software
Luiz Santos / Nicollas Hubie


# DIAGRAMA DE CLASSE 

[UML Class Diagram (1).pdf](https://github.com/user-attachments/files/22642930/UML.Class.Diagram.1.pdf)

Link para melhor vizualização: https://miro.com/welcomeonboard/d2hnWXpSeHRoSWhMWTNkYURhM3NqTkFWN052ZUdpenNIRnZObXcvcVJyMXI3VDFsaW10YXZPOXdLb0FZVjVuc05hYzdRbFErdXdJN3dDb1JHYm1lU2duUXVxV3hUNWFXTFZvdjgvdFIvQUxjVEJveHRGcTh1OGJLMTQ1ZS96djJBS2NFMDFkcUNFSnM0d3FEN050ekl3PT0hdjE=?share_link_id=794451705996

# EXPLICAÇÃO DO CÓDIGO

Documentação Técnica: Explicação da Arquitetura do Código
# 1. Introdução e Objetivo
O projeto consiste em um sistema de gerenciamento em Java que simula uma Pokédex e um Gerenciador de Treinadores, tendo como objetivo principal demonstrar a aplicação correta dos conceitos fundamentais e avançados da Programação Orientada a Objetos (POO). A arquitetura é modular, garantindo a separação de responsabilidades.

# 2. Aplicação de Conceitos de POO
O sistema foi estruturado para aplicar os três pilares da Programação Orientada a Objetos:

# 2.1. Encapsulamento
Definição: Todos os atributos nas classes de modelo (Pokemon, Treinador, Habilidade) foram definidos como privados (private).

Acesso Controlado: O acesso e a modificação desses dados são realizados exclusivamente por meio de métodos públicos (getters e setters), garantindo a integridade dos objetos e protegendo contra alterações indesejadas.

# 2.2. Polimorfismo
Contratos: O polimorfismo é aplicado por meio da criação de interfaces que atuam como contratos de comportamento: Catalogo e CatalogoTreinador.

Implementação: As classes GerenciadorPokemon e GerenciadorTreinador implementam essas interfaces e sobrescrevem os métodos CRUD (como adicionar e buscar), cada um com sua lógica específica.

# 2.3. Herança e Tratamento de Exceções
Herança: O conceito de herança é utilizado para criar uma exceção especializada: PokemonNaoEncontradoException. Esta classe herda de RuntimeException, promovendo uma estrutura organizada para lidar com erros de lógica de negócio (ex: quando uma busca falha em GerenciadorPokemon).

Tratamento Robusto: O código inclui blocos try-catch para gerenciar exceções de I/O (leitura/escrita de arquivos) e garantir que o sistema lide com entradas de dados inválidas de forma controlada.

# 3. Estrutura da Arquitetura do Código
A arquitetura do projeto pode ser dividida nas seguintes camadas funcionais:

Camada	Classes Envolvidas	Responsabilidade
Modelos (Entidades)
Pokemon, Treinador, Habilidade	
Definem a estrutura de dados e as características das entidades. 
A classe Pokemon utiliza uma Collection (ArrayList) para armazenar múltiplas Habilidades.

Contratos	
Catalogo, CatalogoTreinador	
Fornecem as assinaturas dos métodos de CRUD, aplicando o Polimorfismo.

Lógica de Negócio	
GerenciadorPokemon, GerenciadorTreinador	
Contêm a lógica para manipular os dados, validar regras de negócio (ex: limite de 6 Pokémons por treinador) e coordenar a persistência. 
Utilizam Map (HashMap) internamente para processar modificações de dados.

Persistência / I/O	
GerenciadorTxt, Logs	
Gerenciam a leitura e escrita em arquivos de texto (Pokedex.txt, Treinadores.txt). 
A classe Logs fornece um serviço de auditoria, registrando todas as ações de CRUD no log.txt.
Interface (UI)	Menus, Main	A classe Menus exibe a interface de console ao usuário. A classe Main atua como o ponto de entrada e coordenador da aplicação.

Testes Unitários	
TestComandos	
Módulo que utiliza JUnit para validar isoladamente a lógica de classes puras (Habilidade) e métodos utilitários (formatarNome), garantindo a integridade funcional do projeto.
