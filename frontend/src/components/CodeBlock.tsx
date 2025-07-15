import { Stack, TextField } from '@fluentui/react';
import React from 'react';
import GraphOutput from './GraphOutput';
import './CodeBlock.css';

type CodeBlockProps = {
  index: number
  inputs: number
  executing: boolean
  updateExecutionState: (index: number, executing: boolean) => void
}

const CodeBlock: React.FC<CodeBlockProps> = (props) => {
  const [inputIndex, setInputIndex] = React.useState(" ")
  const [input, setInput] = React.useState("")
  const [output, setOutput] = React.useState("");
  const [graphs, setGraphs] = React.useState<Array<string>>([])

  const onInputChanges = React.useCallback((_, value: string | undefined) => {
    setInput(value ?? "")
  }, [])

  const execute = React.useCallback(async () => {
    try {
      setInputIndex(String(props.inputs))
      props.updateExecutionState(props.index, true)
      setOutput("Executing...")
      setGraphs([])

      const response = await fetch("http://localhost:8080/execute", {
        method: "POST",
        body: input,
      })

      if (!response.ok) {
        throw new Error(`Server error: ${response.status} ${response.statusText}`);
      }

      const results = await response.json()
      console.log(results)
      setOutput(results?.logs ?? "")
      setGraphs(results?.graphs ?? [])
    } catch (error) {
      console.error(error);
      const errorMessage = error instanceof Error ? error.message : "An unknown error occurred";
      setOutput(`Error: ${errorMessage}`);
    } finally {
      props.updateExecutionState(props.index, false)
    }
  }, [props, input])

  const onKeyDown = React.useCallback((event: React.KeyboardEvent) => {
    if (event.ctrlKey && event.key === 'Enter') {
      event.preventDefault();
      execute();
    }
  }, [execute]);

  return (
    <Stack tokens={{childrenGap: 10}}>
      <Stack horizontal tokens={{childrenGap: 15}}>
        <Stack>
          <span className="block-label">
            In [{inputIndex}]:
          </span>
          <button
            onClick={execute}
            disabled={props.executing}
            className="execute-button"
            title="Execute Code"
            aria-label="Execute Code"
          >
            <svg
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0 0 24 24"
            >
              <path d="M8 5v14l11-7z" />
            </svg>
          </button>
        </Stack>
        <Stack.Item grow>
          <TextField
            value={input}
            onChange={onInputChanges}
            onKeyDown={onKeyDown}
            multiline
            autoAdjustHeight
            spellCheck={false}
            style={{fontFamily: "monospace", fontSize: 14}} />
        </Stack.Item> 
      </Stack>
      {(output.length > 0 || graphs.length > 0) && (
        <Stack horizontal tokens={{childrenGap: 15}}>
          <span className="block-label-out">
            Out[{inputIndex}]:
          </span>
          <Stack tokens={{childrenGap: 10}}>
            {output && (
              <pre className="code-text">
                <code>
                  {output}
                </code>
              </pre>
            )}
            {graphs.map((g, i) => (
              <GraphOutput graph={g} key={i} />
            ))}
          </Stack>
        </Stack>
      )}
    </Stack>
  )
}

export default CodeBlock;