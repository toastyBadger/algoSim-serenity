algoSim-serenity
================

Multimodal node graph interpreter and simulator for MST/BFS/DFS/etc algortihms

###Plan for next build###

I made 2 new classes;
  - processedGraph
  - graphStage

these should be used to hold the completed graph (i.e. the full MST for now) and each step between the original graph and completely processed graph.

each stage of the graph should hold the complete graph, and which of the nodes should be highlighted / marked as visited. the same applies for the edges. also the text accompanying each stage should be stored here, which nodes are in the queue and information on what is being done.

one other thing that this will entail is to remove the trace code from the algorithms, which will clean them up a bit, though we will still need to indicate where the colours and text need to be applied. i'm not expecting this to be clean or tidy in the slightest... gonna require a few hacks here and there i'm sure!

anyway! good luck and god speed mentlegen!
